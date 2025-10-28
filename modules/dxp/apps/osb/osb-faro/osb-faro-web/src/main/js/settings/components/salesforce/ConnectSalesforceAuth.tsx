import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';
import {Text} from '@clayui/core';

const ConnectSalesforceAuth = ({inputs, onInputsChange}) => {
	const [isUrlCopied, setIsUrlCopied] = useState(false);

	return (
		<>
			<ClayForm.Group>
				<label htmlFor='targetUrl'>
					<Text weight='semi-bold'>
						{Liferay.Language.get('target-url')}
					</Text>

					<div>
						<Text color='secondary' size={3} weight='normal'>
							{Liferay.Language.get(
								'this-is-analytics-cloud-callback-url-salesforce-will-redirect-to-after-a-user-authorizes-the-connection'
							)}
						</Text>
					</div>
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							id='targetUrl'
							insetAfter
							onChange={({target: {value}}) => {
								onInputsChange({
									...inputs,
									callbackURL: value
								});
							}}
							readOnly
							type='text'
							value={inputs.callbackURL}
						/>

						<div className='input-group-inset-item input-group-inset-item-after'>
							<ClayButton
								displayType='unstyled'
								onClick={() => setIsUrlCopied(true)}
								title={Liferay.Language.get('copy')}
							>
								<ClayIcon
									aria-label={Liferay.Language.get('copy')}
									symbol={isUrlCopied ? 'check' : 'copy'}
								/>
							</ClayButton>
						</div>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor='salesforceDataSource'>
					{Liferay.Language.get('salesforce-data-source')}

					<ClayIcon
						aria-label={Liferay.Language.get(
							'salesforce-data-source'
						)}
						className='ml-1 reference-mark'
						symbol='asterisk'
					/>
				</label>

				<ClayInput
					id='salesforceDataSource'
					onChange={({target: {value}}) => {
						onInputsChange({
							...inputs,
							salesForceDataSource: value
						});
					}}
					type='text'
					value={inputs.salesForceDataSource}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor='clientId'>
					{Liferay.Language.get('consumer-key-client-id')}

					<ClayIcon
						aria-label={Liferay.Language.get(
							'consumer-key-client-id'
						)}
						className='ml-1 reference-mark'
						symbol='asterisk'
					/>
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							id='clientId'
							insetAfter
							onChange={({target: {value}}) => {
								onInputsChange({
									...inputs,
									clientId: {
										...inputs.clientId,
										value
									}
								});
							}}
							type={inputs.clientId.visible ? 'text' : 'password'}
							value={inputs.clientId.value}
						/>

						<div className='input-group-inset-item input-group-inset-item-after'>
							<ClayButton
								aria-label={Liferay.Language.get('client-id')}
								displayType='unstyled'
								onClick={() => {
									onInputsChange({
										...inputs,
										clientId: {
											...inputs.clientId,
											visible: !inputs.clientId.visible
										}
									});
								}}
							>
								<ClayIcon
									aria-label={
										inputs.clientId.visible
											? Liferay.Language.get('view')
											: Liferay.Language.get('hidden')
									}
									symbol={
										inputs.clientId.visible
											? 'view'
											: 'hidden'
									}
								/>
							</ClayButton>
						</div>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor='clientSecret'>
					{Liferay.Language.get('consumer-secret-client-secret')}

					<ClayIcon
						className='ml-1 reference-mark'
						symbol='asterisk'
					/>
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							id='clientSecret'
							insetAfter
							onChange={({target: {value}}) => {
								onInputsChange({
									...inputs,
									clientSecret: {
										...inputs.clientSecret,
										value
									}
								});
							}}
							type={
								inputs.clientSecret.visible
									? 'text'
									: 'password'
							}
							value={inputs.clientSecret.value}
						/>

						<div className='input-group-inset-item input-group-inset-item-after'>
							<ClayButton
								aria-label={Liferay.Language.get(
									'client-secret'
								)}
								displayType='unstyled'
								onClick={() => {
									onInputsChange({
										...inputs,
										clientSecret: {
											...inputs.clientSecret,
											visible: !inputs.clientSecret
												.visible
										}
									});
								}}
							>
								<ClayIcon
									aria-label={
										inputs.clientSecret.visible
											? Liferay.Language.get('view')
											: Liferay.Language.get('hidden')
									}
									symbol={
										inputs.clientSecret.visible
											? 'view'
											: 'hidden'
									}
								/>
							</ClayButton>
						</div>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>
		</>
	);
};

export {ConnectSalesforceAuth};
