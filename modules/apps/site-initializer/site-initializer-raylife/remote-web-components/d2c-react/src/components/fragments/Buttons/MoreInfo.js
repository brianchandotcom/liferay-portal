import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

import {useCustomEvent} from '../../../hooks/useCustomEvent';

export const MoreInfoButton = ({callback, event, selected, value}) => {
	// eslint-disable-next-line no-unused-vars
	const [dispatchEvent] = useCustomEvent(event);

	const updateState = () => {
		dispatchEvent({
			...value,
			hide: selected,
		});
		callback();
	};

	return (
		<button
			className={classNames('btn badge', {
				open: selected,
			})}
			onClick={updateState}
			type="button"
		>
			More Info
			<ClayIcon symbol="question-circle-full" />
		</button>
	);
};
