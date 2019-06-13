import {act, renderHook} from 'react-hooks-testing-library';
import {useErrors} from '../ErrorsStore';

test('Should test without error', () => {
	const {result} = renderHook(() => useErrors());

	act(() => result.current.setErrors({}));
	expect(result.current.errors).toMatchObject({});
});

test('Should test with error', () => {
	const {result} = renderHook(() => useErrors());

	act(() => result.current.setErrors({test: 'test'}));
	expect(result.current.errors).toMatchObject({test: 'test'});
});