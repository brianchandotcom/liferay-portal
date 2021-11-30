import classNames from 'classnames';
import React from 'react';
import {useCustomEvent} from '../../../hooks/useCustomEvent';

export function MoreInfoButton({callback, event, selected, value}) {
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
			className={classNames('btn badge more-info', {
				open: selected,
			})}
			onClick={updateState}
			type="button"
		>
			More Info
			<div
				className={classNames('question-circle', {
					selected,
				})}
			>
				<span>&#63;</span>
			</div>
		</button>
	);
}
