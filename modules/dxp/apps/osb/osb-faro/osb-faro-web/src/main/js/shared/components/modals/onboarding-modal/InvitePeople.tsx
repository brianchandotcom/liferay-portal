import * as API from 'shared/api';
import BaseScreen from './BaseScreen';
import ClayButton from '@clayui/button';
import InfoPopover from 'shared/components/InfoPopover';
import Input from 'shared/components/Input';
import InputList, {Display} from 'shared/components/InputList';
import Label from 'shared/components/form/Label';
import Loading, {Align} from 'shared/components/Loading';
import Modal from 'shared/components/modal';
import React, {useState} from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {connect, ConnectedProps} from 'react-redux';
import {UserRoleNames} from 'shared/util/constants';
import {validateEmail} from 'shared/util/email-validators';

const connector = connect(null, {addAlert});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IInvitePeopleProps extends PropsFromRedux {
	dxpConnected: boolean;
	groupId: string;
	onClose: () => void;
	onNext: (increment?: number) => void;
}

const InvitePeople: React.FC<IInvitePeopleProps> = ({
	addAlert,
	dxpConnected,
	groupId,
	onClose,
	onNext
}) => {
	const [emails, setEmails] = useState([]);
	const [inputValue, setInputValue] = useState('');
	const [loading, setLoading] = useState(false);
	const [sent, setSent] = useState(false);

	const handleSubmit = () => {
		if (
			(emails.length && !inputValue) ||
			(inputValue && validateEmail(inputValue))
		) {
			setLoading(true);

			API.user
				.inviteMany({
					emailAddresses: emails,
					groupId,
					roleName: UserRoleNames.Member
				})
				.then(users => {
					setLoading(false);
					setSent(true);

					analytics.track(
						'Invited Users',
						{
							userIds: users.map(({id}) => id)
						},
						{ip: '0'}
					);
				})
				.catch(() => {
					addAlert({
						alertType: Alert.Types.Error,
						message: Liferay.Language.get(
							'unable-to-send-request.-please-try-again-later'
						),
						timeout: false
					});

					setLoading(false);
				});
		}
	};

	return (
		<BaseScreen className='invite-people' onClose={onClose}>
			<Modal.Body className='d-flex flex-column align-items-center flex-grow-1 justify-content-start'>
				{/* TODO: LRAC-7427 Adjust SVGs with Linear Gradients */}
				<div className='ac-invite' />

				<span className='title d-flex justify-content-center'>
					{sent
						? Liferay.Language.get('your-invite-was-sent')
						: Liferay.Language.get('invite-people-to-workspace')}
				</span>

				{sent ? (
					<div className='description text-center'>
						<h4>
							{Liferay.Language.get(
								'you-can-see-the-new-members-invitation-status-and-role-permissions-under-user-management-in-settings'
							)}
						</h4>
					</div>
				) : (
					<div className='add-emails'>
						<Label>
							{Liferay.Language.get('add-other-members')}

							<InfoPopover
								className='ml-2'
								content={Liferay.Language.get(
									'each-users-role-can-be-set-under-user-management-in-settings'
								)}
								title={Liferay.Language.get(
									'member-permissions'
								)}
							/>
						</Label>

						<Input.Group>
							<Input.GroupItem>
								<InputList
									errorAttr={{
										className: 'has-warning',
										icon: {display: Display.Warning}
									}}
									errorMessage={Liferay.Language.get(
										'please-enter-a-valid-email-address'
									)}
									inputValue={inputValue}
									items={emails}
									onInputChange={setInputValue}
									onItemsChange={setEmails}
									placeholder={Liferay.Language.get(
										'enter-email-address'
									)}
									validateOnBlur
									validationFn={validateEmail}
								/>
							</Input.GroupItem>
						</Input.Group>

						<div className='secondary-info'>
							{Liferay.Language.get(
								'enter-email-addresses-separated-by-spaces-or-commas'
							)}
						</div>
					</div>
				)}
			</Modal.Body>

			<Modal.Footer className='d-flex justify-content-end'>
				<ClayButton
					className='button-root'
					disabled={sent}
					displayType='secondary'
					onClick={dxpConnected ? () => onNext() : onClose}
				>
					{Liferay.Language.get('skip')}
				</ClayButton>

				<ClayButton
					className='button-root'
					disabled={
						(!inputValue && !emails.length) ||
						(!!inputValue && !validateEmail(inputValue))
					}
					displayType='primary'
					onClick={
						sent
							? dxpConnected
								? () => onNext()
								: onClose
							: handleSubmit
					}
				>
					{loading && <Loading align={Align.Left} />}

					{sent
						? dxpConnected
							? Liferay.Language.get('next')
							: Liferay.Language.get('done')
						: Liferay.Language.get('send-invitations')}
				</ClayButton>
			</Modal.Footer>
		</BaseScreen>
	);
};

export default connector(InvitePeople);
