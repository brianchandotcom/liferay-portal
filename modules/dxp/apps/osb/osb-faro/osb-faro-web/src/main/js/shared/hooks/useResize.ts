import {MutableRefObject, useEffect, useState} from 'react';

export const useResize = (
	elementRef: MutableRefObject<any> | null
): [number, number] => {
	const [size, setSize] = useState<[number, number]>([0, 0]);

	useEffect(() => {
		const handleResize = () =>
			setSize([
				elementRef?.current?.clientWidth ?? window.innerWidth,
				elementRef?.current?.clientHeight ?? window.innerHeight
			]);

		handleResize();

		window.addEventListener('resize', handleResize);

		return () => window.removeEventListener('resize', handleResize);
	}, [elementRef]);

	return size;
};
