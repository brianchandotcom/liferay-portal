import ClayButton from '@clayui/button';
import getCN from 'classnames';
import Input from 'shared/components/Input';
import InputList from 'shared/components/InputList';
import Modal from 'shared/components/modal';
import Promise from 'metal-promise';
import React, {useState} from 'react';
import {validateEmail} from 'shared/util/email-validators';

interface IInviteUsersModalProps {
	className: string;
	onClose: () => void;
	onSubmit: (emails: string[]) => typeof Promise;
}

const InviteUsersModal: React.FC<IInviteUsersModalProps> = ({
	className,
	onClose,
	onSubmit,
	...otherProps
}) => {
	const [emails, setEmails] = useState<string[]>([]);
	const [inputValue, setInputValue] = useState('');

	const handleSubmit = () => {
		if (
			(emails.length && !inputValue) ||
			(inputValue && validateEmail(inputValue))
		) {
			onSubmit(emails).then(users => {
				analytics.track(
					'Invited Users',
					{
						userIds: users.map(({id}) => id)
					},
					{ip: '0'}
				);
			});
		}
	};

	return (
		<Modal
			{...otherProps}
			className={getCN('invite-users-modal-root', className)}
			size='lg'
		>
			<Modal.Header
				onClose={onClose}
				title={Liferay.Language.get('invite-users')}
			/>

			<Modal.Body>
				<div className='description form-text'>
					{Liferay.Language.get(
						'enter-the-email-addresses-of-the-people-you-would-like-to-invite-to-analytics-cloud.-separate-each-address-by-space-or-comma'
					)}
				</div>

				<Input.Group>
					<InputList
						errorMessage={Liferay.Language.get(
							'please-enter-a-valid-email-address'
						)}
						inputValue={inputValue}
						items={emails}
						onInputChange={value => setInputValue(value.trim())}
						onItemsChange={setEmails}
						placeholder={Liferay.Language.get(
							'enter-email-address'
						)}
						validateOnBlur
						validationFn={validateEmail}
					/>
				</Input.Group>
			</Modal.Body>

			<Modal.Footer>
				<ClayButton
					className='button-root'
					displayType='secondary'
					onClick={onClose}
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>

				<ClayButton
					className='button-root'
					disabled={
						(!inputValue && !emails.length) ||
						(!!inputValue && !validateEmail(inputValue))
					}
					displayType='primary'
					onClick={handleSubmit}
				>
					{Liferay.Language.get('send')}
				</ClayButton>
			</Modal.Footer>
		</Modal>
	);
};

export default InviteUsersModal;
