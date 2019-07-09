import {cancelDebounce, debounce} from '../../../src/main/resources/META-INF/resources/liferay/debounce/debounce.es';

describe('debounce', function() {
	it('should only call received function with the last called args after a delay', function() {
		jest.useFakeTimers();
		
		const fn = jest.fn();

		const debounced = debounce(fn, 200);

		debounced(1, 2, 3);
		debounced(4, 5, 6);

		setTimeout(() => {
			debounced(7, 8, 9);
			debounced(10, 11, 12);
			
			setTimeout(() => {
				expect(fn).toHaveBeenCalledTimes(1);
				expect(fn).toHaveBeenCalledWith(10, 11, 12);
			}, 210);
		}, 100)

		jest.runAllTimers();
	});

	it('should call original function with its original context', function() {
		jest.useFakeTimers();
		
		const expectedContext = {};
		let context;

		const fn = function() {
			context = this; /* eslint-disable-line */
		};

		const debounced = debounce(fn.bind(expectedContext), 200);

		debounced(1, 2, 3);

		setTimeout(function() {
			expect(expectedContext).toBe(context);
		}, 200);

		jest.runAllTimers();
	});

	it('should cancel the debounced function call', function() {
		jest.useFakeTimers();
		
		const fn = jest.fn();

		const debounced = debounce(fn, 200);

		debounced(1, 2, 3);

		cancelDebounce(debounced);

		setTimeout(() => {
			expect(fn).not.toHaveBeenCalled()
		}, 210);

		jest.runAllTimers();
	});

});