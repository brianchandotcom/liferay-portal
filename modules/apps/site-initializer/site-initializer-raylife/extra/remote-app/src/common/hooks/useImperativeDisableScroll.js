import {useEffect} from 'react';

const PROPERTY = {
	overflowY: {
		off: 'hidden',
		on: 'scroll',
	},
	touchAction: {
		off: 'none',
		on: 'auto',
	},
};

const useImperativeDisableScroll = ({disabled, element}) => {
	useEffect(() => {
		if (!element) {
			return;
		}

		element.style['touch-action'] = disabled // on/off scroll for Mobile Phone
			? PROPERTY.touchAction.off
			: PROPERTY.touchAction.on;

		element.style.overflowY = disabled // on/off scroll for Desktop Browser
			? PROPERTY.overflowY.off
			: PROPERTY.overflowY.on;

		return () => {
			element.style['touch-action'] = PROPERTY.overflowY.on;
			element.style.overflowY = PROPERTY.overflowY.on;
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [disabled]);
};

export default useImperativeDisableScroll;
