/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import AOP from '../../../src/main/resources/META-INF/resources/liferay/aop/AOP.es';

let callOrder = [];

const addSpy = jest.fn().mockImplementation(() => callOrder.push('addSpy'));
const spy1 = jest.fn().mockImplementation(() => callOrder.push('spy1'));
const spy2 = jest.fn().mockImplementation(() => callOrder.push('spy2'));

describe('Ajax', () => {
	beforeEach(() => {
		addSpy.mockClear();
		spy1.mockClear();
		spy2.mockClear();
		callOrder = [];
	});

	it('calls listener before original method', () => {
		const obj = new MyClass();

		AOP.before(spy1, obj, 'add');

		const retVal = obj.add(1, 2);

		expect(addSpy).toHaveBeenCalledTimes(1);
		expect(spy1).toHaveBeenCalledTimes(1);
		expect(callOrder).toEqual(['spy1', 'addSpy']);
		expect(spy1).toHaveBeenCalledWith(1, 2);
		expect(retVal).toEqual(3);
	});

	it('calls listener after original method', () => {
		const obj = new MyClass();

		AOP.after(spy1, obj, 'add');

		const retVal = obj.add(1, 2);

		expect(addSpy).toHaveBeenCalledTimes(1);
		expect(spy1).toHaveBeenCalledTimes(1);
		expect(callOrder).toEqual(['addSpy', 'spy1']);
		expect(spy1).toHaveBeenCalledWith(1, 2);
		expect(retVal).toEqual(3);
	});

	it('calls multiple listeners in correct order', () => {
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

	it('does not call listener if returned handle is removed', () => {
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

	it('only removes listeners that are detached', () => {
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

	it('prevents wrapped function from firing when AOP.prevent is returned by listener', () => {
		const obj = new MyClass();

		AOP.before(
			() => {
				return AOP.prevent();
			},
			obj,
			'add'
		);

		obj.add(1, 2);

		expect(addSpy).not.toHaveBeenCalled();
	});

	it('prevents wrapped function and all further before subscribers from firing when AOP.halt is returned by listener', () => {
		const obj = new MyClass();

		AOP.before(
			() => {
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

	it('prevents all further after subscribers from firing when AOP.halt is returned by listener', () => {
		const obj = new MyClass();

		AOP.after(
			() => {
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

	it('modifies return value when AOP.alterReturn is returned by `after` listener', () => {
		const obj = new MyClass();

		AOP.after(
			() => {
				return AOP.alterReturn(AOP.currentRetVal + 1);
			},
			obj,
			'add'
		);

		const retVal = obj.add(1, 2);

		expect(addSpy).toHaveBeenCalledTimes(1);
		expect(retVal).toEqual(4);
	});

	it('tracks changes made to return value with subsequent changes made by AOP.alterReturn', () => {
		const obj = new MyClass();

		AOP.after(
			() => {
				expect(AOP.currentRetVal).toEqual(3);

				return AOP.alterReturn(22);
			},
			obj,
			'add'
		);
		AOP.after(
			() => {
				expect(AOP.currentRetVal).toEqual(22);

				return AOP.alterReturn('now a string');
			},
			obj,
			'add'
		);
		AOP.after(
			() => {
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

	it('tracks original return value when changes are made by AOP.alterReturn', () => {
		const obj = new MyClass();

		AOP.after(
			() => {
				expect(AOP.currentRetVal).toEqual(3);

				return AOP.alterReturn(22);
			},
			obj,
			'add'
		);
		AOP.after(
			() => {
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
