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
import {sub} from 'frontend-js-web';
import React, {
	SetStateAction,
	useCallback,
	useEffect,
	useRef,
	useState,
} from 'react';

// @ts-ignore

import ServiceProvider from '../../ServiceProvider/index';
import ErrorMessage, {showError} from './ErrorMessage';
import {
	IAPIResponseError,
	ICountry,
	ICountryAPIResponse,
	IFieldError,
	IPostalAddress,
	IPostalAddressAPIResponse,
	IRegion,
} from './Types';

interface IAddressSelectorProps {
	setHandleNameChange(name: string): void;
	setHandleSubmit(
		callback: SetStateAction<(event: Event) => Promise<IPostalAddress>>
	): void;
	setIsFormValid(value: boolean): void;
	accountId: number;
	addressId?: number;
	addressType?: string;
	hasManageAddressesPermission?: boolean;
	label?: string;
	namespace?: string;
}

const MANDATORY_FIELDS = [
	'addressCountry',
	'addressLocality',
	'addressRegion',
	'name',
	'postalCode',
	'streetAddressLine1',
];

function AddressSelector({
	accountId,
	addressId = 0,
	addressType = 'shipping',
	hasManageAddressesPermission = true,
	label = Liferay.Language.get('delivery-group'),
	namespace = 'AddressSelector',
	setHandleNameChange,
	setHandleSubmit,
	setIsFormValid,
}: IAddressSelectorProps) {
	const defaultAddressRef: React.MutableRefObject<IPostalAddress> = useRef({
		addressType,
		id: 0,
		primary: false,
	});

	const [addresses, setAddresses] = useState<Array<IPostalAddress>>([]);
	const [countries, setCountries] = useState<Array<ICountry>>([]);
	const [errors, setErrors] = useState<IFieldError>(
		MANDATORY_FIELDS.reduce((map: IFieldError, field: string) => {
			map[field] = '';

			return map;
		}, {})
	);
	const [regions, setRegions] = useState<Array<IRegion>>([]);
	const [currentAddress, setCurrentAddress] = useState<IPostalAddress>(
		defaultAddressRef?.current
	);

	const handleAddressIdChange = useCallback(
		({target: {value}}) => {
			const address = addresses.find(
				(address) => address.id === Number(value)
			);

			setCurrentAddress(() => address || defaultAddressRef.current);
			if (Number(value) === 0) {
				setErrors(
					MANDATORY_FIELDS.reduce((map: IFieldError, field) => {
						map[field] = '';

						return map;
					}, {})
				);
			}
			else {
				setErrors({});

				setHandleNameChange(address?.name || '');
			}
		},
		[addresses, setHandleNameChange]
	);

	const handleFieldChange = useCallback(
		({
			target: {name: fieldName, value},
		}: {
			target: {
				name: string;
				value: boolean | number | string | undefined;
			};
		}) => {
			setCurrentAddress((prevState) => {
				return {
					...prevState,
					addressRegion:
						fieldName === 'addressCountry'
							? null
							: prevState.addressRegion,
					[fieldName]: value,
				} as IPostalAddress;
			});

			if (MANDATORY_FIELDS.includes(fieldName)) {
				setErrors((prevState) => {
					if (
						(!value && fieldName !== 'addressRegion') ||
						(!value &&
							fieldName === 'addressRegion' &&
							!!regions.length)
					) {
						return {
							...prevState,
							[fieldName]: Liferay.Language.get(
								'this-field-is-required'
							),
						};
					}
					else {
						delete prevState[fieldName];

						return {
							...prevState,
						};
					}
				});
			}
		},
		[regions]
	);

	useEffect(() => {
		let data: Array<IRegion> = [];

		if (currentAddress.addressCountry) {
			data = (
				countries.find(
					(country) => country.name === currentAddress.addressCountry
				)?.regions || []
			).reduce((data, region) => {
				if (region.active) {
					region.label =
						region.title_i18n[
							Liferay.ThemeDisplay.getLanguageId()
						] || region.title_i18n['en_US'];

					data.push(region);
				}

				return data;
			}, [] as Array<IRegion>);

			setRegions(data);
		}
		else {
			setRegions(data);
		}

		setErrors((prevState) => {
			if (!currentAddress.id && data.length) {
				prevState['addressRegion'] = '';
			}
			else {
				delete prevState['addressRegion'];
			}

			return {
				...prevState,
			};
		});
	}, [countries, currentAddress.addressCountry, currentAddress.id]);

	useEffect(() => {
		ServiceProvider.AdminUserAPI('v1')
			.getPostalAddresses(accountId)
			.then(({items}: IPostalAddressAPIResponse) => {
				const data = items.filter(
					(item) =>
						item.addressType === 'billing-and-shipping' ||
						item.addressType === addressType
				);
				setAddresses(data);

				if (addressId) {
					setCurrentAddress(
						() =>
							data.find(
								(address) => address.id === Number(addressId)
							) || defaultAddressRef.current
					);

					setErrors({});
				}
			})
			.catch((error: IAPIResponseError) => {
				setAddresses([]);

				showError(error);
			});
	}, [accountId, addressId, addressType]);

	useEffect(() => {
		ServiceProvider.AdminAddressAPI('v1')
			.getCountries({
				pageSize: -1,
			})
			.then((data: ICountryAPIResponse) => {
				setCountries(
					(data?.items || [])
						.reduce((data, country) => {
							if (country.active) {
								country.name =
									country.title_i18n[
										Liferay.ThemeDisplay.getLanguageId()
									] || country.title_i18n['en_US'];

								data.push(country);
							}

							return data;
						}, [] as Array<ICountry>)
						.sort((item1, item2) =>
							item1.name.localeCompare(item2.name)
						)
				);
			})
			.catch((error: IAPIResponseError) => {
				setCountries([]);

				showError(error);
			});
	}, []);

	useEffect(() => {
		setHandleSubmit(() => async (event: Event): Promise<IPostalAddress> => {
			event.preventDefault();

			if (!currentAddress.id) {
				MANDATORY_FIELDS.forEach((field) => {
					handleFieldChange({
						target: {name: field, value: currentAddress[field]},
					});
				});

				if (Object.keys(errors).length) {
					return Promise.resolve({id: 0});
				}

				return ServiceProvider.AdminUserAPI('v1')
					.postPostalAddress(accountId, currentAddress)
					.then((response: IPostalAddress) => {
						return response;
					})
					.catch((error: IAPIResponseError) => {
						showError(error);

						return Promise.resolve({id: 0});
					});
			}
			else {
				return Promise.resolve(currentAddress);
			}
		});
	}, [accountId, currentAddress, errors, handleFieldChange, setHandleSubmit]);

	useEffect(() => {
		setIsFormValid(!Object.keys(errors).length);
	}, [errors, setIsFormValid]);

	return (
		<>
			<label htmlFor={`${namespace}addressId`}>
				{sub(Liferay.Language.get('choose-x'), label)}
			</label>

			<ClaySelect
				className="mb-3"
				id={`${namespace}addressId`}
				name="addressId"
				onChange={handleAddressIdChange}
				required={true}
				value={currentAddress.id || 0}
			>
				{hasManageAddressesPermission && (
					<ClaySelect.Option
						aria-label={Liferay.Language.get('add-new-address')}
						label={Liferay.Language.get('add-new-address')}
						value={0}
					/>
				)}

				{addresses.map((address) => (
					<ClaySelect.Option
						aria-label={address.name}
						key={address.id}
						label={address.name}
						value={address.id}
					/>
				))}
			</ClaySelect>

			<div className="row">
				<div className="col-12">
					<ClayForm.Group
						className={classnames({
							'has-error': !!errors.name,
						})}
					>
						<label htmlFor={`${namespace}name`}>
							{Liferay.Language.get('address-name')}

							<ClayIcon
								className="c-ml-1 reference-mark"
								symbol="asterisk"
							/>
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('name')}
							className="mb-3"
							disabled={!!currentAddress.id}
							id={`${namespace}name`}
							name="name"
							onChange={handleFieldChange}
							required={!currentAddress.id}
							type="text"
							value={currentAddress?.name || ''}
						/>

						<ErrorMessage errors={errors} name="name" />
					</ClayForm.Group>

					<ClayForm.Group
						className={classnames({
							'has-error': !!errors.addressCountry,
						})}
					>
						<label htmlFor={`${namespace}addressCountry`}>
							{Liferay.Language.get('country')}

							<ClayIcon
								className="c-ml-1 reference-mark"
								symbol="asterisk"
							/>
						</label>

						<ClaySelectWithOption
							className="mb-3"
							disabled={!!currentAddress.id}
							id={`${namespace}addressCountry`}
							name="addressCountry"
							onChange={handleFieldChange}
							options={[
								{},
								...countries.map((country) => {
									return {
										'aria-label': country.name,
										'label': country.name,
										'value': country.name,
									};
								}),
							]}
							required={!currentAddress.id}
							value={currentAddress?.addressCountry || ''}
						/>

						<ErrorMessage errors={errors} name="addressCountry" />
					</ClayForm.Group>

					<ClayForm.Group
						className={classnames({
							'has-error': !!errors.streetAddressLine1,
						})}
					>
						<label htmlFor={`${namespace}streetAddressLine1`}>
							{Liferay.Language.get('address-line-1')}

							<ClayIcon
								className="c-ml-1 reference-mark"
								symbol="asterisk"
							/>
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('address-line-1')}
							className="mb-3"
							disabled={!!currentAddress.id}
							id={`${namespace}streetAddressLine1`}
							name="streetAddressLine1"
							onChange={handleFieldChange}
							required={!currentAddress.id}
							type="text"
							value={currentAddress?.streetAddressLine1 || ''}
						/>

						<ErrorMessage
							errors={errors}
							name="streetAddressLine1"
						/>
					</ClayForm.Group>
				</div>
			</div>

			<div className="row">
				<div className="col-6">
					<ClayForm.Group>
						<label htmlFor={`${namespace}streetAddressLine2`}>
							{Liferay.Language.get('address-line-2')}
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('address-line-2')}
							className="mb-3"
							disabled={!!currentAddress.id}
							id={`${namespace}streetAddressLine2`}
							name="streetAddressLine2"
							onChange={handleFieldChange}
							type="text"
							value={currentAddress?.streetAddressLine2 || ''}
						/>
					</ClayForm.Group>
				</div>

				<div className="col-6">
					<ClayForm.Group>
						<label htmlFor={`${namespace}streetAddressLine3`}>
							{Liferay.Language.get('address-line-3')}
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('address-line-3')}
							className="mb-3"
							disabled={!!currentAddress.id}
							id={`${namespace}streetAddressLine3`}
							name="streetAddressLine3"
							onChange={handleFieldChange}
							type="text"
							value={currentAddress?.streetAddressLine3 || ''}
						/>
					</ClayForm.Group>
				</div>
			</div>

			<div className="row">
				<div className="col-6">
					<ClayForm.Group
						className={classnames({
							'has-error': !!errors.addressLocality,
						})}
					>
						<label htmlFor={`${namespace}addressLocality`}>
							{Liferay.Language.get('city')}

							<ClayIcon
								className="c-ml-1 reference-mark"
								symbol="asterisk"
							/>
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('city')}
							className="mb-3"
							disabled={!!currentAddress.id}
							id={`${namespace}addressLocality`}
							name="addressLocality"
							onChange={handleFieldChange}
							required={!currentAddress.id}
							type="text"
							value={currentAddress?.addressLocality || ''}
						/>

						<ErrorMessage errors={errors} name="addressLocality" />
					</ClayForm.Group>
				</div>

				<div className="col-6">
					<ClayForm.Group
						className={classnames({
							'has-error': !!errors.addressRegion,
						})}
					>
						<label htmlFor={`${namespace}addressRegion`}>
							{Liferay.Language.get('region')}

							{!!regions.length && (
								<ClayIcon
									className="c-ml-1 reference-mark"
									symbol="asterisk"
								/>
							)}
						</label>

						<ClaySelectWithOption
							className="mb-3"
							disabled={!!currentAddress.id || !regions.length}
							id={`${namespace}addressRegion`}
							name="addressRegion"
							onChange={handleFieldChange}
							options={[
								{},
								...regions.map((region) => {
									return {
										'aria-label': region.name,
										'label': region.label,
										'value': region.name,
									};
								}),
							]}
							required={!currentAddress.id && !!regions.length}
							value={currentAddress?.addressRegion || ''}
						/>

						<ErrorMessage errors={errors} name="addressRegion" />
					</ClayForm.Group>
				</div>
			</div>

			<div className="row">
				<div className="col-6">
					<ClayForm.Group
						className={classnames({
							'has-error': !!errors.postalCode,
						})}
					>
						<label htmlFor={`${namespace}postalCode`}>
							{Liferay.Language.get('zip')}

							<ClayIcon
								className="c-ml-1 reference-mark"
								symbol="asterisk"
							/>
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('zip')}
							className="mb-3"
							disabled={!!currentAddress.id}
							id={`${namespace}postalCode`}
							name="postalCode"
							onChange={handleFieldChange}
							required={!currentAddress.id}
							type="text"
							value={currentAddress?.postalCode || ''}
						/>

						<ErrorMessage errors={errors} name="postalCode" />
					</ClayForm.Group>
				</div>

				<div className="col-6">
					<ClayForm.Group>
						<label htmlFor={`${namespace}phoneNumber`}>
							{Liferay.Language.get('phone-number')}
						</label>

						<ClayInput
							aria-label={Liferay.Language.get('phone-number')}
							className="mb-3"
							disabled={!!currentAddress.id}
							id={`${namespace}phoneNumber`}
							name="phoneNumber"
							onChange={handleFieldChange}
							type="text"
							value={currentAddress?.phoneNumber || ''}
						/>
					</ClayForm.Group>
				</div>
			</div>
		</>
	);
}

export default AddressSelector;
