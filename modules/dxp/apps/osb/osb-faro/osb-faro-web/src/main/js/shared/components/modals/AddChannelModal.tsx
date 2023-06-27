import ClayButton from '@clayui/button';
import Form, {
	validateMaxLength,
	validateMinLength,
	validateRequired
} from 'shared/components/form';
import Loading, {Align} from 'shared/components/Loading';
import Modal from 'shared/components/modal';
import React from 'react';
import {Modal as ModalTypes} from 'shared/types';
import {sequence} from 'shared/util/promise';

interface IAddChannelModalProps extends React.HTMLAttributes<HTMLElement> {
	onCloseFn: ModalTypes.close;
	onSubmitFn: (value: {name: string}) => void;
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
						<ClayButton
							className='button-root'
							displayType='secondary'
							onClick={onCloseFn}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							className='button-root'
							disabled={isSubmitting || !isValid}
							displayType='primary'
							type='submit'
						>
							{isSubmitting && <Loading align={Align.Left} />}

							{Liferay.Language.get('save')}
						</ClayButton>
					</Modal.Footer>
				</Form.Form>
			)}
		</Form>
	</Modal>
);

export default AddChannelModal;
