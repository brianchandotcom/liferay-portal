import Alert, {AlertTypes} from 'shared/components/Alert';
import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import Loading, {Align} from 'shared/components/Loading';
import React, {useState} from 'react';
import {Observer, Size, Status} from '@clayui/modal/lib/types';

interface IBaseModalProps extends React.HTMLAttributes<HTMLElement> {
	cancelMessage?: string;
	children: React.ReactNode;
	disabled?: boolean;
	observer: Observer;
	onClose: () => void;
	onSubmit: () => Promise<void | any> | void;
	onCancel?: () => void;
	onSuccess?: () => void;
	size?: Size;
	status?: Status;
	submitMessage: string;
	title: string;
}

const BaseModal: React.FC<IBaseModalProps> = ({
	cancelMessage = Liferay.Language.get('cancel'),
	children,
	disabled = false,
	observer,
	onCancel,
	onClose,
	onSubmit,
	onSuccess,
	size,
	status,
	submitMessage,
	title
}) => {
	const [submitting, setSubmitting] = useState(false);
	const [submitError, setSubmitError] = useState(null);

	let buttonSubmitProps: any = {
		className: `button-root d-flex align-items-center btn-${status}`,
		disabled: disabled || submitting
	};

	if (!disabled) {
		buttonSubmitProps = {
			...buttonSubmitProps,
			onClick: () => {
				setSubmitting(true);

				const submitVal = onSubmit();

				if (submitVal instanceof Promise) {
					submitVal
						.then(() => {
							setSubmitting(false);

							onSuccess && onSuccess();
							onClose();
						})
						.catch(error => {
							console.error(error); // eslint-disable-line no-console
							setSubmitError(error);

							setSubmitting(false);
						});
				} else {
					setSubmitting(false);

					onSuccess && onSuccess();
					onClose();
				}
			}
		};
	}

	return (
		<ClayModal observer={observer} size={size} status={status}>
			<ClayModal.Header>{title}</ClayModal.Header>
			<ClayModal.Body>
				{submitError && (
					<Alert type={AlertTypes.Danger}>
						{Liferay.Language.get('sorry-an-error-occurred')}
					</Alert>
				)}

				{children}
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType='secondary'
							onClick={onCancel || onClose}
						>
							{cancelMessage}
						</ClayButton>
						<ClayButton {...buttonSubmitProps}>
							{submitting && <Loading align={Align.Left} />}

							{submitMessage}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

export default BaseModal;
