import {useEffect} from 'react';

const useImperativeDisableScroll = ({disabled, element}) => {
	useEffect(() => {
		if (!element) {
			return;
		}

		element.style.overflowY = disabled ? 'hidden' : 'scroll';

		return () => {
			element.style.overflowY = 'scroll';
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [disabled]);
};

export default useImperativeDisableScroll;
