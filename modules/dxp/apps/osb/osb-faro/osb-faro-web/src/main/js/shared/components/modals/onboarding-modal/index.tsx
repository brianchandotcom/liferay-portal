import ConnectDXP from './ConnectDXP';
import InvitePeople from './InvitePeople';
import Modal from 'shared/components/modal';
import React, {useState} from 'react';
import ReadyToGo from './ReadyToGo';
import Welcome from './Welcome';

const MODAL_SCREENS = [Welcome, ConnectDXP, InvitePeople, ReadyToGo];

interface IOnboardingModalProps {
	groupId: string;
	onClose: () => void;
}

const OnboardingModal: React.FC<IOnboardingModalProps> = ({
	groupId,
	onClose
}) => {
	const [dxpConnected, setDxpConnected] = useState(false);
	const [step, setStep] = useState(0);

	const ScreenComponent = MODAL_SCREENS[step];

	return (
		<Modal className='onboarding-modal-root'>
			<ScreenComponent
				dxpConnected={dxpConnected}
				groupId={groupId}
				onboarding
				onClose={onClose}
				onDxpConnected={setDxpConnected}
				onNext={(increment = 1) => setStep(step + increment)}
				onPrevious={() => setStep(step - 1)}
			/>
		</Modal>
	);
};

export default OnboardingModal;
