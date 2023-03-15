import Button from 'shared/components/Button';
import getCN from 'classnames';
import Modal from 'shared/components/modal';
import Promise from 'metal-promise';
import React, {useState} from 'react';
import {noop} from 'lodash';

interface IConfirmationModalProps extends React.HTMLAttributes<HTMLDivElement> {
	cancelMessage?: string;
	closeAfterSubmit?: boolean;
	message: string;
	modalVariant?: string;
	onClose: () => void;
	onSubmit: () => any;
	submitButtonDisplay?: string;
	submitMessage?: string;
	title?: string;
	titleIcon?: string;
}

const ConfirmationModal: React.FC<IConfirmationModalProps> = ({
	cancelMessage = Liferay.Language.get('cancel'),
	className,
	closeAfterSubmit = true,
	message,
	modalVariant = '',
	onClose = noop,
	onSubmit = noop,
	submitButtonDisplay = 'primary',
	submitMessage = Liferay.Language.get('continue'),
	title = Liferay.Language.get('confirm'),
	titleIcon,
	...otherProps
}) => {
	const [submitting, setSubmitting] = useState<boolean>(false);

	return (
		<Modal
			{...otherProps}
			className={getCN('confirmation-modal-root', className, {
				[modalVariant]: modalVariant
			})}
		>
			<Modal.Header
				iconSymbol={titleIcon}
				onClose={onClose}
				title={title}
			/>

			<Modal.Body>{message}</Modal.Body>

			<Modal.Footer>
				<Button onClick={onClose}>{cancelMessage}</Button>

				<Button
					display={submitButtonDisplay}
					loading={submitting}
					onClick={() => {
						setSubmitting(true);

						const submitVal: any = onSubmit();

						if (submitVal instanceof Promise) {
							(submitVal as typeof Promise)
								.then(() => {
									setSubmitting(false);

									closeAfterSubmit && onClose();
								})
								.catch(() => {
									setSubmitting(false);
								});
						} else {
							setSubmitting(false);

							closeAfterSubmit && onClose();
						}
					}}
				>
					{submitMessage}
				</Button>
			</Modal.Footer>
		</Modal>
	);
};

export default ConfirmationModal;
