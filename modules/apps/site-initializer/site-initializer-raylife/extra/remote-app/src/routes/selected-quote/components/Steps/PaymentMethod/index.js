import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import classNames from 'classnames';
import {useContext, useEffect, useState} from 'react';
import {getLiferaySiteName} from '../../../../../common/utils/liferay';
import {SelectedQuoteContext} from '../../../context/SelectedQuoteContextProvider';
import {getPaymentMethodURL, getPaymentMethods} from '../../../services/Cart';
import {updateOrderPaymentMethod} from '../../../services/Order';
import RadioButton from './RadioButton';

const PaymentMethod = () => {
	const [agree, setAgree] = useState(false);
	const [{orderId, product}] = useContext(SelectedQuoteContext);
	const [methods, setMethods] = useState([]);
	const productDiscounted = Number(product.price) * 0.95;
	const productPromo = Number(product.price) * 0.05;

	useEffect(() => {
		if (orderId) {
			getPaymentMethods(orderId).then((response) => {
				const {
					data: {items},
				} = response;

				const siteName = getLiferaySiteName().replace('/web/', '');

				const methodList = items.map((item) => ({
					checked: false,
					image: `/webdav/${siteName}/document_library/${item.key.replace(
						'-',
						'_'
					)}.svg`,
					options: [
						{
							checked: true,
							description: `Save $${productPromo.toLocaleString(
								'en-US'
							)}`,
							id: 0,
							title: `Pay in full – $${productDiscounted.toLocaleString(
								'en-US'
							)}`,
						},
						{
							checked: false,
							description: `1 additional payment of $${Number(
								product.price / 2
							).toLocaleString('en-US')}`,
							id: 1,
							title: `2 payments of $${Number(
								product.price / 2
							).toLocaleString('en-US')}`,
						},
					],
					title: item.name,
					value: item.key,
				}));

				setMethods(methodList);
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [orderId]);

	const onSelectedMethod = (value) => {
		setMethods(
			methods.map((method) => ({
				...method,
				checked: method.value === value,
			}))
		);
	};

	const onSelectedOption = (optionId) => {
		setMethods(
			methods?.map((method) => ({
				...method,
				options: method.options.map((option) => ({
					...option,
					checked: option.id === optionId,
				})),
			}))
		);
	};

	const onClickPayNow = async (method) => {
		await updateOrderPaymentMethod(method.value, product.price, orderId);

		const {data: paymentMethodURL} = await getPaymentMethodURL(
			orderId,
			`${origin}${getLiferaySiteName()}/congrats`
		);

		window.location.href = paymentMethodURL;
	};

	const showOptions = (method) =>
		method.options.map((option, index) => (
			<div
				className={classNames(
					'align-items-center c-mr-3 c-px-5 c-py-3 d-flex d-flex flex-column justify-content-center rounded-sm',
					{
						'border': !option.checked,
						'selected': option.checked,
						'shadow-lg': option.checked,
						'type-payment-card-solid': option.checked,
					}
				)}
				key={index}
				onClick={() => onSelectedOption(option.id)}
			>
				<div>
					<p className="text-center text-link-md">{option.title}</p>

					<p
						className={classNames('text-center', {
							'font-weight-bold text-accent-5 text-paragraph-xs':
								option.checked,
							'text-paragraph-xs': !option.checked,
						})}
					>
						{option.description}
					</p>
				</div>
			</div>
		));

	const checkedMethod = methods.find(({checked}) => checked);

	return (
		<div className="c-mb-4 c-mt-5 ml-6">
			<div className="c-mb-3 c-mt-5 d-flex flex-column">
				<h5 className="mb-3">Payment Method</h5>

				{methods.map((method, index) => (
					<div
						className="align-items-center c-mb-3 d-flex flex-row payment-method"
						key={index}
					>
						<RadioButton
							onSelected={onSelectedMethod}
							selected={method.checked}
							value={method.value}
						>
							<>
								<div className="align-items-center d-flex justify-content-center">
									<div>
										<img
											className="bg-neutral-0 border c-p-1 card-outlined pay-card-image rounded-sm"
											src={method.image}
										/>
									</div>
								</div>

								<p>{method.title}</p>
							</>
						</RadioButton>
					</div>
				))}
			</div>

			<div className="c-mb-5 d-flex flex-column">
				{checkedMethod && (
					<>
						<h5 className="c-mb-3">Billing Options</h5>

						{checkedMethod.options.length ? (
							<>
								<div className="c-mb-3 d-flex flex-row">
									{showOptions(checkedMethod)}
								</div>
								<div className="d-flex flex-row">
									<div className="agree-check c-mr-2">
										<ClayCheckbox
											checked={agree}
											name="agree-check"
											onChange={() => setAgree(!agree)}
											type="checkbox"
											value={agree}
										/>
									</div>

									<p className="align-items-center c-mb-6 d-flex justify-content-center">
										I have read and agree to the&nbsp;
										<strong>
											Raylife Terms and Conditions
										</strong>
									</p>
								</div>
								<div className="c-mb-2 c-mt-10 d-flex justify-content-end payment-button">
									<ClayButton
										className="btn-solid c-px-5 display-4 text-link-md text-uppercase"
										disabled={!agree}
										displayType="style-secondary"
										onClick={() =>
											onClickPayNow(checkedMethod)
										}
									>
										Pay Now
									</ClayButton>
								</div>
								{checkedMethod.value === 'paypal' && (
									<p className="d-flex justify-content-end option-message">
										You will be redirected to PayPal to
										complete payment
									</p>
								)}
							</>
						) : (
							<div className="no-options">
								<p>No options...</p>
							</div>
						)}
					</>
				)}
			</div>
		</div>
	);
};

export default PaymentMethod;
