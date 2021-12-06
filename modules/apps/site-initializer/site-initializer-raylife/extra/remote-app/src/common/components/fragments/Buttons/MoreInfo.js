import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
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
		<ClayLabel
			className={classNames(
				'btn-more-info label-inverse-primary rounded-sm',
				{
					'label-solid-info': selected,
				}
			)}
			onClick={updateState}
		>
			<div className="align-items-center d-flex">
				<span className="text-paragraph-sm">More Info</span>

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
