import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import React, {useContext} from 'react';
import {WebContentContext} from '../../../../routes/get-a-quote/context/WebContentProvider';

export function MoreInfoButton({
	callback,
	event,
	label = 'More Info',
	selected,
	value,
}) {
	const [, dispatchEvent] = useContext(WebContentContext);

	const updateState = () => {
		dispatchEvent(
			{
				...value,
				hide: selected,
			},
			event
		);
		callback();
	};

	return (
		<ClayLabel
			className={classNames('btn-info-panel rounded-sm', {
				'label-inverse-primary': selected,
				'label-tonal-primary': !selected,
			})}
			onClick={updateState}
		>
			<div className="align-items-center d-flex mx-2">
				<span className="text-paragraph-sm">{label}</span>

				<span className="inline-item inline-item-after">
					<ClayIcon
						symbol={
							selected
								? 'question-circle-full'
								: 'question-circle'
						}
					/>
				</span>
			</div>
		</ClayLabel>
	);
}
