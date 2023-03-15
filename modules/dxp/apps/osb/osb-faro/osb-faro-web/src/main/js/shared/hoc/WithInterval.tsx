import React, {useCallback, useState} from 'react';
import {Interval} from 'shared/types';
import {INTERVAL_KEY_MAP} from 'shared/util/time';

interface IWrappedComponentProps {
	onChangeInterval: (val) => void;
	interval: Interval;
}

const withInterval = (
	WrappedComponent: React.ComponentType<IWrappedComponentProps>
) => props => {
	const [interval, setInterval] = useState(INTERVAL_KEY_MAP.day);
	const handleChangeInterval = useCallback(newVal => setInterval(newVal), []);

	return (
		<WrappedComponent
			{...props}
			interval={interval}
			onChangeInterval={handleChangeInterval}
		/>
	);
};

export default withInterval;
