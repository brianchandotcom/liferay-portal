import {useEffect} from 'react';

export const useInterval = <ReturnType>(
	tickFn: () => ReturnType,
	delay: number = 0
): void => {
	let intervalId;

	useEffect(() => {
		intervalId = setInterval(tickFn, delay);

		return () => {
			clearInterval(intervalId);
		};
	}, [delay]);
};
