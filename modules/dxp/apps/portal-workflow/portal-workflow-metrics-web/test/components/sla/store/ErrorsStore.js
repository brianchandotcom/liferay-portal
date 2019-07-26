import {act, renderHook} from '@testing-library/react-hooks';
import {useErrors} from '../../../../src/main/resources/META-INF/resources/js/components/sla/store/ErrorsStore';

test('Should test with error', () => {
	const {result} = renderHook(() => useErrors());

	act(() => result.current.setErrors({test: 'test'}));
	expect(result.current.errors).toMatchObject({test: 'test'});
});

test('Should test without error', () => {
	const {result} = renderHook(() => useErrors());

	act(() => result.current.setErrors({}));
	expect(result.current.errors).toMatchObject({});
});
