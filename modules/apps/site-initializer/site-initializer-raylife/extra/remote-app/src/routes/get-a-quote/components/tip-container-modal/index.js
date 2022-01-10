import Modal from '../../../../common/components/modal';
import {useTriggerContext} from '../../hooks/useTriggerContext';

const TipContainerModal = ({isMobile, setWebContentModal, webContentModal}) => {
	const {updateState} = useTriggerContext();

	const onClose = () => {
		setWebContentModal({html: '', show: false});
		updateState('');
	};

	return (
		<Modal
			backdropLight={isMobile}
			closeable={false}
			onClose={onClose}
			show={webContentModal.show}
			size="small-mobile"
		>
			{webContentModal.html && (
				<>
					<div
						className="tip-container-modal"
						dangerouslySetInnerHTML={{
							__html: webContentModal.html,
						}}
					/>

					<div className="d-flex justify-content-center">
						<button
							className="btn btn-inverted btn-rounded btn-style-primary shadow-none text-uppercase"
							onClick={onClose}
						>
							Dismiss
						</button>
					</div>
				</>
			)}
		</Modal>
	);
};

export default TipContainerModal;
