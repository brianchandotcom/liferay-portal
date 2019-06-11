import AOP from '../../../src/main/resources/META-INF/resources/liferay/aop/AOP.es';

let callOrder = [];

const addSpy = jest.fn().mockImplementation(() => callOrder.push('addSpy'));
const spy1 = jest.fn().mockImplementation(() => callOrder.push('spy1'));
const spy2 = jest.fn().mockImplementation(() => callOrder.push('spy2'));

describe('Ajax', function() {
	beforeEach(function() {
		addSpy.mockClear();
		spy1.mockClear();
		spy2.mockClear();
		callOrder = [];
	});

	it('should call listener before original method', function() {
		const obj = new MyClass();

		AOP.before(spy1, obj, 'add');

		const retVal = obj.add(1, 2);

		expect(addSpy).toHaveBeenCalledTimes(1);
		expect(spy1).toHaveBeenCalledTimes(1);
		expect(callOrder).toEqual(['spy1', 'addSpy']);
		expect(spy1).toHaveBeenCalledWith(1, 2);
		expect(retVal).toEqual(3);
	});

	it('should call listener after original method', function() {
		const obj = new MyClass();

		AOP.after(spy1, obj, 'add');

		const retVal = obj.add(1, 2);

		expect(addSpy).toHaveBeenCalledTimes(1);
		expect(spy1).toHaveBeenCalledTimes(1);
		expect(callOrder).toEqual(['addSpy', 'spy1']);
		expect(spy1).toHaveBeenCalledWith(1, 2);
		expect(retVal).toEqual(3);
	});

	it('should call multiple listeners in correct order', function() {
		const obj = new MyClass();

		AOP.before(spy1, obj, 'add');
		AOP.before(spy2, obj, 'add');

		const retVal = obj.add(1, 2);

		expect(addSpy).toHaveBeenCalledTimes(1);
		expect(spy1).toHaveBeenCalledTimes(1);
		expect(spy2).toHaveBeenCalledTimes(1);
		expect(callOrder).toEqual(['spy1', 'spy2', 'addSpy']);
		expect(spy1).toHaveBeenCalledWith(1, 2);
		expect(spy2).toHaveBeenCalledWith(1, 2);
		expect(retVal).toEqual(3);
	});

	it('should not call listener if returned handle is removed', function() {
		const obj = new MyClass();

		const handle = AOP.before(spy1, obj, 'add');

		obj.add(1, 2);

		expect(addSpy).toHaveBeenCalledTimes(1);
		expect(spy1).toHaveBeenCalledTimes(1);

		handle.detach();

		obj.add(1, 2);

		expect(addSpy).toHaveBeenCalledTimes(2);
		expect(spy1).toHaveBeenCalledTimes(1);
	});

	it('should only remove listeners that are detached', function() {
		const obj = new MyClass();

		const handle1 = AOP.before(spy1, obj, 'add');
		AOP.before(spy2, obj, 'add');

		obj.add(1, 2);

		expect(addSpy).toHaveBeenCalledTimes(1);
		expect(spy1).toHaveBeenCalledTimes(1);
		expect(spy2).toHaveBeenCalledTimes(1);

		handle1.detach();

		obj.add(1, 2);

		expect(addSpy).toHaveBeenCalledTimes(2);
		expect(spy1).toHaveBeenCalledTimes(1);
		expect(spy2).toHaveBeenCalledTimes(2);
	});

	it('should prevent wrapped function from firing when AOP.prevent is returned by listener', function() {
		const obj = new MyClass();

		AOP.before(
			function() {
				return AOP.prevent();
			},
			obj,
			'add'
		);

		obj.add(1, 2);

		expect(addSpy).not.toHaveBeenCalled();
	});

	it('should prevent wrapped function and all further before subscribers from firing when AOP.halt is returned by listener', function() {
		const obj = new MyClass();

		AOP.before(
			function() {
				return AOP.halt('new value');
			},
			obj,
			'add'
		);
		AOP.before(spy1, obj, 'add');

		const retVal = obj.add(1, 2);

		expect(addSpy).not.toHaveBeenCalled();
		expect(spy1).not.toHaveBeenCalled();
		expect(retVal).toEqual('new value');
	});

	it('should prevent all further after subscribers from firing when AOP.halt is returned by listener', function() {
		const obj = new MyClass();

		AOP.after(
			function() {
				return AOP.halt('new value');
			},
			obj,
			'add'
		);
		AOP.after(spy1, obj, 'add');

		const retVal = obj.add(1, 2);

		expect(spy1).not.toHaveBeenCalled();
		expect(retVal).toEqual('new value');
	});

	it('should modify return value when AOP.alterReturn is returned by `after` listener', function() {
		const obj = new MyClass();

		AOP.after(
			function() {
				return AOP.alterReturn(AOP.currentRetVal + 1);
			},
			obj,
			'add'
		);

		const retVal = obj.add(1, 2);

		expect(addSpy).toHaveBeenCalledTimes(1);
		expect(retVal).toEqual(4);
	});

	it('should track changes made to return value with subsequent changes made by AOP.alterReturn', function() {
		const obj = new MyClass();

		AOP.after(
			function() {
				expect(AOP.currentRetVal).toEqual(3);

				return AOP.alterReturn(22);
			},
			obj,
			'add'
		);
		AOP.after(
			function() {
				expect(AOP.currentRetVal).toEqual(22);

				return AOP.alterReturn('now a string');
			},
			obj,
			'add'
		);
		AOP.after(
			function() {
				expect(AOP.currentRetVal).toEqual('now a string');

				return AOP.alterReturn(AOP.currentRetVal + ':');
			},
			obj,
			'add'
		);

		const retVal = obj.add(1, 2);

		expect(addSpy).toHaveBeenCalledTimes(1);
		expect(retVal).toEqual('now a string:');
	});

	it('should track original return value when changes are made by AOP.alterReturn', function() {
		const obj = new MyClass();

		AOP.after(
			function() {
				expect(AOP.currentRetVal).toEqual(3);

				return AOP.alterReturn(22);
			},
			obj,
			'add'
		);
		AOP.after(
			function() {
				expect(AOP.currentRetVal).toEqual(22);

				return AOP.alterReturn(AOP.originalRetVal);
			},
			obj,
			'add'
		);

		const retVal = obj.add(1, 2);

		expect(addSpy).toHaveBeenCalledTimes(1);
		expect(retVal).toEqual(3);
	});
});

class MyClass {
	add(n1, n2) {
		addSpy();

		return n1 + n2;
	}
}
