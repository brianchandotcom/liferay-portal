/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import classNames from 'classnames';

// @ts-ignore

import {CommerceServiceProvider, liferayNavigate} from 'commerce-frontend-js';
import React, {useState} from 'react';

// @ts-ignore

import {createCommerceCart} from '../../../utilities/createCommerceCart';

// @ts-ignore

import {regenerateOrderDetailURL} from '../../../utilities/regenerateOrderDetailURL';
import LABELS from '../labels';
import {IRequestQuoteCPInstance, IRequestQuoteChannel} from '../types';

interface IRequestQuoteFormProps {
	accountId: string;
	baseOrderDetailURL?: string;
	cartId?: string;
	channel: IRequestQuoteChannel;
	cpInstance?: IRequestQuoteCPInstance;
	createCart?: boolean;
	defaultEmail?: string;
	namespace: string;
	notesPermission?: boolean;
	onOpenChange: (value: boolean) => void;
	orderDetailURL?: string;
	orderUUID?: string;
	restrictedNotesPermission?: boolean;
}

const RequestQuoteForm = ({
	accountId,
	baseOrderDetailURL,
	cartId,
	channel,
	cpInstance,
	createCart = false,
	defaultEmail = '',
	namespace,
	notesPermission = false,
	onOpenChange,
	orderDetailURL,
	orderUUID,
	restrictedNotesPermission = false,
}: IRequestQuoteFormProps) => {
	const [emailAddress, setEmailAddress] = useState(defaultEmail);
	const [emailAddressError, setEmailAddressError] = useState<string | null>(
		null
	);
	const [isPrivate, setIsPrivate] = useState(false);
	const [isSubmitting, setIsSubmitting] = useState(false);
	const [note, setNote] = useState('');

	const onFormSubmit = async () => {
		setIsSubmitting(true);

		let currentCartId = cartId;
		let currentOrderUUID = orderUUID;

		if (createCart && cpInstance) {
			const newCart = await createCommerceCart({
				accountId,
				cartItems: [
					{
						options: JSON.stringify(
							cpInstance.skuOptions || JSON.stringify([])
						),
						quantity: cpInstance.quantity || 1,
						skuId: cpInstance.skuId,
						skuUnitOfMeasure: cpInstance.skuUnitOfMeasure,
					},
				],
				commerceChannelId: channel.id,
				skipRedirect: true,
			});

			currentCartId = newCart.id;
			currentOrderUUID = newCart.orderUUID;
		}

		if (currentCartId) {
			await CommerceServiceProvider.DeliveryCartAPI(
				'v1'
			).executeCartTransitionsById(currentCartId, {
				comment: note,
				name: 'request-quote',
				restricted: isPrivate,
			});
		}

		let currentOrderDetailURL = orderDetailURL || '';
		if (
			orderDetailURL &&
			orderDetailURL.includes(encodeURIComponent('{id}')) &&
			currentCartId
		) {
			currentOrderDetailURL = currentOrderDetailURL.replace(
				encodeURIComponent('{id}'),
				currentCartId
			);
		}

		const redirectURL =
			currentOrderDetailURL ||
			regenerateOrderDetailURL(
				baseOrderDetailURL,
				currentCartId,
				currentOrderUUID
			);

		if (redirectURL) {
			liferayNavigate(redirectURL);
		}

		setIsSubmitting(false);
		onOpenChange(false);
	};

	const validateEmailAddress = () => {
		const EMAIL_REGEX = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;
		const emailAddressError = EMAIL_REGEX.test(emailAddress)
			? null
			: LABELS.INVALID_EMAIL_ADDRESS;
		setEmailAddressError(emailAddressError);
	};

	return (
		<ClayForm onSubmit={(event) => event.preventDefault()}>
			<ClayForm.Group className="mb-3">
				<ClayInput.GroupItem
					className={classNames(
						{'has-error': emailAddressError},
						'pb-4',
						'position-relative',
						'w-100'
					)}
				>
					<ClayInput
						aria-label={LABELS.EMAIL_ADDRESS_FIELD}
						disabled={isSubmitting}
						id={`${namespace}email-address`}
						name={`${namespace}email-address`}
						onBlur={validateEmailAddress}
						onChange={(event) =>
							setEmailAddress(event.target.value)
						}
						placeholder={LABELS.EMAIL_ADDRESS_FIELD}
						type="email"
						value={emailAddress}
					/>

					{emailAddressError && (
						<span
							className="font-weight-semi-bold position-absolute text-danger"
							style={{bottom: 0}}
						>
							{emailAddressError}
						</span>
					)}
				</ClayInput.GroupItem>

				<ClayInput
					className="mt-2"
					component="textarea"
					disabled={isSubmitting}
					id={`${namespace}note`}
					name={`${namespace}note`}
					onChange={(event) => setNote(event.target.value)}
					placeholder={LABELS.TYPE_NOTE}
					value={note}
				/>

				{(notesPermission || restrictedNotesPermission) && (
					<ClayInput.Group>
						<ClayInput.GroupItem className="align-items-center c-gapx-2 d-flex flex-row justify-content-end mt-3">
							<ClayCheckbox
								checked={isPrivate}
								id={`${namespace}private`}
								label={LABELS.PRIVATE}
								name={`${namespace}private`}
								onChange={(event) =>
									setIsPrivate(event.target.checked)
								}
							/>

							<ClayPopover
								alignPosition="top"
								closeOnClickOutside
								role="tooltip"
								trigger={
									<span className="mb-2">
										<ClayIcon symbol="question-circle-full" />
									</span>
								}
							>
								{LABELS.RESTRICTED_HELP}
							</ClayPopover>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				)}
			</ClayForm.Group>

			<ClayButton.Group className="justify-content-end w-100" spaced>
				<ClayButton
					displayType="secondary"
					onClick={() => onOpenChange(false)}
				>
					{LABELS.CANCEL}
				</ClayButton>

				<ClayButton
					disabled={isSubmitting || emailAddressError !== null}
					onClick={onFormSubmit}
				>
					{LABELS.SUBMIT}
				</ClayButton>
			</ClayButton.Group>
		</ClayForm>
	);
};

export default RequestQuoteForm;
