import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import {useCustomEvent} from '../../../../../../../common/hooks/useCustomEvent';
import {TIP_EVENT} from '../../../../../../../common/utils/events';

import {useTriggerContext} from '../../../../../hooks/useTriggerContext';

const InfoPanelButton = ({selectedStep}) => {
	const {isSelected, updateState} = useTriggerContext();
	const templateName = 'i-am-unable-to-find-my-industry';
	const [dispatchEvent] = useCustomEvent(TIP_EVENT);

	const selectedTrigger = isSelected(templateName);

	const showInfoPanel = () => {
		updateState(templateName);

		dispatchEvent({
			hide: selectedTrigger,
			step: selectedStep,
			templateName,
		});
	};

	return (
		<ClayLabel
			className={classNames('btn-info-panel mt-3', {
				'label-inverse-primary': selectedTrigger,
				'label-tonal-primary': !selectedTrigger,
			})}
			onClick={showInfoPanel}
		>
			<div className="align-items-center d-flex mx-2">
				<span className="text-paragraph-sm">
					I am unable to find my industry
				</span>

				<span className="inline-item inline-item-after">
					<ClayIcon
						symbol={
							selectedTrigger
								? 'question-circle-full'
								: 'question-circle'
						}
					/>
				</span>
			</div>
		</ClayLabel>
	);
};

export default InfoPanelButton;
