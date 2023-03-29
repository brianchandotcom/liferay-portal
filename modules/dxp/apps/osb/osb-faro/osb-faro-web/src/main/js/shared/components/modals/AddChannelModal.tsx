import Button from 'shared/components/Button';
import Form, {
	validateMaxLength,
	validateMinLength,
	validateRequired
} from 'shared/components/form';
import Modal from 'shared/components/modal';
import React from 'react';
import {Modal as ModalTypes} from 'shared/types';
import {sequence} from 'shared/util/promise';

interface IAddChannelModalProps extends React.HTMLAttributes<HTMLElement> {
	onCloseFn: ModalTypes.close;
	onSubmitFn: ({name: string}) => void;
}

const AddChannelModal: React.FC<IAddChannelModalProps> = ({
	onCloseFn,
	onSubmitFn
}) => (
	<Modal className='add-channel-modal'>
		<Form
			initialValues={{
				name: ''
			}}
			onSubmit={onSubmitFn}
		>
			{({handleSubmit, isSubmitting, isValid}) => (
				<Form.Form onSubmit={handleSubmit}>
					<Modal.Header
						onClose={onCloseFn}
						title={Liferay.Language.get('new-property')}
					/>

					<Modal.Body>
						<Form.Input
							autoFocus
							label={Liferay.Language.get('property-name')}
							name='name'
							validate={sequence([
								validateRequired,
								validateMaxLength(65),
								validateMinLength(3)
							])}
						/>
					</Modal.Body>

					<Modal.Footer>
						<Button display='secondary' onClick={onCloseFn}>
							{Liferay.Language.get('cancel')}
						</Button>

						<Button
							disabled={isSubmitting || !isValid}
							display='primary'
							loading={isSubmitting}
							type='submit'
						>
							{isSubmitting
								? Liferay.Language.get('saving')
								: Liferay.Language.get('save')}
						</Button>
					</Modal.Footer>
				</Form.Form>
			)}
		</Form>
	</Modal>
);

export default AddChannelModal;
