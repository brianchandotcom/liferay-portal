/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {
	ClayInput,
	ClaySelect,
	ClaySelectWithOption,
} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import {CommerceServiceProvider} from 'commerce-frontend-js';
import {openToast, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

const InfoBoxModalAddressInput = ({
	additionalProps,
	field,
	inputValue,
	label,
	orderId,
	setHandleSubmit,
	setInputValue,
	setParseRequest,
	setParseResponse,
	submitOrder,
}) => {
	const [addresses, setAddresses] = useState([]);
	const [countries, setCountries] = useState([]);
	const [errors, setErrors] = useState({});
	const [hasRegions, setHasRegions] = useState(false);

	const getAddressType = () => {
		if (field === 'billingAddress') {
			return 'billing';
		}

		if (field === 'shippingAddress') {
			return 'shipping';
		}

		return null;
	};

	const [currentAddress, setCurrentAddress] = useState({
		addressType: getAddressType(),
		id: 0,
		primary: false,
	});

	const checkRequiredFields = (currentAddress) => {
		if (currentAddress.id !== 0) {
			return false;
		}

		const curErrors = {};

		if (!currentAddress.addressCountry) {
			curErrors['infoBoxModalAddressCountryInput'] = Liferay.Language.get(
				'this-field-is-required'
			);
		}

		if (!currentAddress.addressLocality) {
			curErrors['infoBoxModalAddressCityInput'] = Liferay.Language.get(
				'this-field-is-required'
			);
		}

		if (!currentAddress.addressRegion && hasRegions) {
			curErrors['infoBoxModalAddressRegionInput'] = Liferay.Language.get(
				'this-field-is-required'
			);
		}

		if (!currentAddress.name) {
			curErrors['infoBoxModalAddressNameInput'] = Liferay.Language.get(
				'this-field-is-required'
			);
		}

		if (!currentAddress.postalCode) {
			curErrors['infoBoxModalAddressZipInput'] = Liferay.Language.get(
				'this-field-is-required'
			);
		}

		if (!currentAddress.streetAddressLine1) {
			curErrors['infoBoxModalAddressStreet1Input'] = Liferay.Language.get(
				'this-field-is-required'
			);
		}

		setErrors((prevState) => ({
			...prevState,
			...curErrors,
		}));

		return Object.keys(curErrors).length;
	};

	const ErrorMessage = ({errors, name}) => {
		return (
			<>
				{!!errors[name] && (
					<ClayForm.FeedbackItem>
						<ClayForm.FeedbackIndicator symbol="exclamation-full" />

						{errors[name]}
					</ClayForm.FeedbackItem>
				)}
			</>
		);
	};

	const handleAddressChange = ({target: {value}}) => {
		setInputValue(value);
	};

	const onChangeHandler = ({target}) => {
		const curErrors = errors;

		if (!target.value || target.value.length <= 0) {
			curErrors[target.id] = Liferay.Language.get(
				'this-field-is-required'
			);
		}
		else {
			delete curErrors[target.id];
		}

		setErrors((prevState) => ({
			...prevState,
			...curErrors,
		}));
	};

	useEffect(() => {
		const [selectedAddress] = addresses.filter(
			(address) => address.id === Number(inputValue)
		);

		setCurrentAddress(
			selectedAddress
				? selectedAddress
				: {addressType: getAddressType(), id: 0, primary: false}
		);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [addresses, inputValue]);

	useEffect(() => {
		if (currentAddress.id !== 0) {
			setErrors({});
		}

		setHandleSubmit(() => async (event) => {
			event.preventDefault();

			if (currentAddress.id === 0) {
				if (checkRequiredFields(currentAddress)) {
					return;
				}

				CommerceServiceProvider.AdminUserAPI('v1')
					.postPostalAddress(
						Liferay.CommerceContext.account.accountId,
						currentAddress
					)
					.then((response) => {
						submitOrder(response.id);
					})
					.catch((error) => {
						openToast({
							message:
								error.detail ||
								error.errorDescription ||
								Liferay.Language.get(
									'an-unexpected-system-error-occurred'
								),
							type: 'danger',
						});
					});
			}
			else {
				submitOrder(Number(inputValue));
			}
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [currentAddress]);

	useEffect(() => {
		CommerceServiceProvider.AdminUserAPI('v1')
			.getPostalAddresses(Liferay.CommerceContext.account.accountId)
			.then(({items}) => {
				setAddresses(
					items.filter(
						(item) =>
							item.addressType === 'billing-and-shipping' ||
							item.addressType === getAddressType()
					)
				);
			})
			.catch((error) => {
				setAddresses([]);

				openToast({
					message:
						error.detail ||
						error.errorDescription ||
						Liferay.Language.get(
							'an-unexpected-system-error-occurred'
						),
					type: 'danger',
				});
			});

		CommerceServiceProvider.AdminAddressAPI('v1')
			.getCountries()
			.then((data) => {
				setCountries(
					(data?.items || [])
						.filter((country) => {
							return country.active;
						})
						.map((country) => {
							country.name =
								country.title_i18n[
									Liferay.ThemeDisplay.getLanguageId()
								] || country.title_i18n['en_US'];

							return country;
						})
						.sort((a, b) => {
							return a.name > b.name
								? 1
								: b.name > a.name
									? -1
									: 0;
						})
				);
			})
			.catch((error) => {
				setCountries([]);

				openToast({
					message:
						error.detail ||
						error.errorDescription ||
						Liferay.Language.get(
							'an-unexpected-system-error-occurred'
						),
					type: 'danger',
				});
			});

		setParseRequest(() => (field, inputValue) => {
			return {
				[field + 'Id']: Number(inputValue),
			};
		});
		setParseResponse(() => (field, response) => {
			return response[field];
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			<label htmlFor="infoBoxModalAddressInput">
				{sub(Liferay.Language.get('choose-x'), label)}
			</label>

			<ClaySelect
				className="mb-3"
				id="infoBoxModalAddressInput"
				name={orderId}
				onChange={handleAddressChange}
			>
				{additionalProps.hasManageAddressesPermission ? (
					<ClaySelect.Option
						aria-label={Liferay.Language.get('add-new-address')}
						label={Liferay.Language.get('add-new-address')}
						selected={Number(inputValue) === 0}
						value={0}
					/>
				) : null}

				{addresses.map((address) => (
					<ClaySelect.Option
						aria-label={address.streetAddressLine1}
						key={address.id}
						label={address.name}
						selected={address.id === Number(inputValue)}
						value={address.id}
					/>
				))}
			</ClaySelect>

			<div className="row">
				<div className="col-12">
					<ClayForm.Group
						className={classnames({
							'has-error': !!errors.infoBoxModalAddressNameInput,
						})}
					>
						<label htmlFor="infoBoxModalAddressNameInput">
							{Liferay.Language.get('name')}

							<ClayIcon
								className="c-ml-1 reference-mark"
								symbol="asterisk"
							/>
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('name')}
							className="mb-3"
							disabled={currentAddress.id !== 0}
							id="infoBoxModalAddressNameInput"
							onChange={(event) => {
								event.preventDefault();

								setCurrentAddress({
									...currentAddress,
									name: event.target.value,
								});

								onChangeHandler(event);
							}}
							type="text"
							value={currentAddress?.name ?? ''}
						/>

						<ErrorMessage
							errors={errors}
							name="infoBoxModalAddressNameInput"
						/>
					</ClayForm.Group>

					<ClayForm.Group
						className={classnames({
							'has-error':
								!!errors.infoBoxModalAddressCountryInput,
						})}
					>
						<label htmlFor="infoBoxModalAddressCountryInput">
							{Liferay.Language.get('country')}

							<ClayIcon
								className="c-ml-1 reference-mark"
								symbol="asterisk"
							/>
						</label>

						<ClaySelectWithOption
							className="mb-3"
							disabled={currentAddress.id !== 0}
							id="infoBoxModalAddressCountryInput"
							onChange={(event) => {
								event.preventDefault();

								setCurrentAddress({
									...currentAddress,
									addressCountry: event.target.value,
									addressRegion: null,
								});

								setHasRegions(
									!!countries.find(
										(country) =>
											country.name === event.target.value
									)?.regions?.length
								);

								onChangeHandler(event);
							}}
							options={[
								{},
								...countries.map((country) => {
									return {
										'aria-label': country.name,
										'label':
											country.title_i18n[
												Liferay.ThemeDisplay.getLanguageId()
											] || country.title_i18n['en_US'],
										'value': country.name,
									};
								}),
							]}
							value={currentAddress?.addressCountry ?? ''}
						/>

						<ErrorMessage
							errors={errors}
							name="infoBoxModalAddressCountryInput"
						/>
					</ClayForm.Group>

					<ClayForm.Group
						className={classnames({
							'has-error':
								!!errors.infoBoxModalAddressStreet1Input,
						})}
					>
						<label htmlFor="infoBoxModalAddressStreet1Input">
							{Liferay.Language.get('address-line-1')}

							<ClayIcon
								className="c-ml-1 reference-mark"
								symbol="asterisk"
							/>
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('address-line-1')}
							className="mb-3"
							disabled={currentAddress.id !== 0}
							id="infoBoxModalAddressStreet1Input"
							onChange={(event) => {
								event.preventDefault();

								setCurrentAddress({
									...currentAddress,
									streetAddressLine1: event.target.value,
								});

								onChangeHandler(event);
							}}
							type="text"
							value={currentAddress?.streetAddressLine1 ?? ''}
						/>

						<ErrorMessage
							errors={errors}
							name="infoBoxModalAddressStreet1Input"
						/>
					</ClayForm.Group>
				</div>
			</div>

			<div className="row">
				<div className="col-6">
					<ClayForm.Group>
						<label htmlFor="infoBoxModalAddressStreet2Input">
							{Liferay.Language.get('address-line-2')}
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('address-line-2')}
							className="mb-3"
							disabled={currentAddress.id !== 0}
							id="infoBoxModalAddressStreet2Input"
							onChange={(event) => {
								event.preventDefault();

								setCurrentAddress({
									...currentAddress,
									streetAddressLine2: event.target.value,
								});
							}}
							type="text"
							value={currentAddress?.streetAddressLine2 ?? ''}
						/>
					</ClayForm.Group>

					<ClayForm.Group
						className={classnames({
							'has-error': !!errors.infoBoxModalAddressCityInput,
						})}
					>
						<label htmlFor="infoBoxModalAddressCityInput">
							{Liferay.Language.get('city')}

							<ClayIcon
								className="c-ml-1 reference-mark"
								symbol="asterisk"
							/>
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('city')}
							className="mb-3"
							disabled={currentAddress.id !== 0}
							id="infoBoxModalAddressCityInput"
							onChange={(event) => {
								event.preventDefault();

								setCurrentAddress({
									...currentAddress,
									addressLocality: event.target.value,
								});

								onChangeHandler(event);
							}}
							type="text"
							value={currentAddress?.addressLocality ?? ''}
						/>

						<ErrorMessage
							errors={errors}
							name="infoBoxModalAddressCityInput"
						/>
					</ClayForm.Group>

					<ClayForm.Group
						className={classnames({
							'has-error': !!errors.infoBoxModalAddressZipInput,
						})}
					>
						<label htmlFor="infoBoxModalAddressZipInput">
							{Liferay.Language.get('zip')}

							<ClayIcon
								className="c-ml-1 reference-mark"
								symbol="asterisk"
							/>
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('zip')}
							className="mb-3"
							disabled={currentAddress.id !== 0}
							id="infoBoxModalAddressZipInput"
							onChange={(event) => {
								event.preventDefault();

								setCurrentAddress({
									...currentAddress,
									postalCode: event.target.value,
								});

								onChangeHandler(event);
							}}
							type="text"
							value={currentAddress?.postalCode ?? ''}
						/>

						<ErrorMessage
							errors={errors}
							name="infoBoxModalAddressZipInput"
						/>
					</ClayForm.Group>
				</div>

				<div className="col-6">
					<ClayForm.Group>
						<label htmlFor="infoBoxModalAddressStreet3Input">
							{Liferay.Language.get('address-line-3')}
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('address-line-3')}
							className="mb-3"
							disabled={currentAddress.id !== 0}
							id="infoBoxModalAddressStreet3Input"
							onChange={(event) => {
								event.preventDefault();

								setCurrentAddress({
									...currentAddress,
									streetAddressLine3: event.target.value,
								});
							}}
							type="text"
							value={currentAddress?.streetAddressLine3 ?? ''}
						/>
					</ClayForm.Group>

					<ClayForm.Group
						className={classnames({
							'has-error':
								!!errors.infoBoxModalAddressRegionInput,
						})}
					>
						<label htmlFor="infoBoxModalAddressRegionInput">
							{Liferay.Language.get('region')}

							{hasRegions && (
								<ClayIcon
									className="c-ml-1 reference-mark"
									symbol="asterisk"
								/>
							)}
						</label>

						<ClaySelectWithOption
							className="mb-3"
							disabled={currentAddress.id !== 0}
							id="infoBoxModalAddressRegionInput"
							onChange={(event) => {
								event.preventDefault();

								setCurrentAddress({
									...currentAddress,
									addressRegion: event.target.value,
								});

								onChangeHandler(event);
							}}
							options={[
								{},
								...(
									(
										countries.find((country) => {
											return (
												country.name ===
												currentAddress.addressCountry
											);
										}) || {}
									).regions || []
								)
									.filter((region) => {
										return region.active;
									})
									.map((region) => {
										return {
											'aria-label': region.name,
											'label':
												region.title_i18n[
													Liferay.ThemeDisplay.getLanguageId()
												] || region.title_i18n['en_US'],
											'value': region.name,
										};
									}),
							]}
							value={currentAddress?.addressRegion ?? ''}
						/>

						<ErrorMessage
							errors={errors}
							name="infoBoxModalAddressRegionInput"
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<label htmlFor="infoBoxModalAddressPhoneNumberInput">
							{Liferay.Language.get('phone-number')}
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('phone-number')}
							className="mb-3"
							disabled={currentAddress.id !== 0}
							id="infoBoxModalAddressPhoneNumberInput"
							onChange={(event) => {
								event.preventDefault();

								setCurrentAddress({
									...currentAddress,
									phoneNumber: event.target.value,
								});
							}}
							type="text"
							value={currentAddress?.phoneNumber ?? ''}
						/>
					</ClayForm.Group>
				</div>
			</div>
		</>
	);
};

export default InfoBoxModalAddressInput;
