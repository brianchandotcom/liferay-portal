import Modal from '../../../../common/components/modal';

const TipContainerModal = ({setWebContentModal, webContentModal}) => {
	const onClose = () => {
		setWebContentModal({html: '', show: false});
	};

	return (
		<Modal
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
							className="btn btn-inverted btn-rounded btn-style-primary shadow-none"
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
