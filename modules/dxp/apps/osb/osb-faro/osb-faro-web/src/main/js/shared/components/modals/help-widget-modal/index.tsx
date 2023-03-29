import IssueSubmitted from './IssueSubmitted';
import Modal from 'shared/components/modal';
import React, {useState} from 'react';
import ReportIssue from './ReportIssue';

const MODAL_SCREENS = [ReportIssue, IssueSubmitted];

interface IHelpWidgetModalProps {
	groupId: string;
	onClose: () => void;
}

const HelpWidgetModal: React.FC<IHelpWidgetModalProps> = ({
	groupId,
	onClose
}) => {
	const [step, setStep] = useState(0);

	const ScreenComponent = MODAL_SCREENS[step];

	return (
		<Modal className='help-widget-modal-root'>
			<ScreenComponent
				groupId={groupId}
				onClose={onClose}
				onNext={(increment = 1) => setStep(step + increment)}
			/>
		</Modal>
	);
};

export default HelpWidgetModal;
