import classNames from 'classnames';

import {useContext, useEffect, useState} from 'react';
import {LiferayService} from '../../../../../common/services/liferay';
import {SelectedQuoteContext} from '../../../context/SelectedQuoteContextProvider';
import {getPaymentMethods} from '../../../services/Cart';
import {updateOrderPaymentMethod} from '../../../services/Order';

import RadioButton from './RadioButton';

const PaymentMethod = () => {
	const [agree, setAgree] = useState(false);
	const [{orderId, product}] = useContext(SelectedQuoteContext);
	const [methods, setMethods] = useState([]);

	useEffect(() => {
		if (orderId) {
			getPaymentMethods(orderId).then((response) => {
				const {
					data: {items},
				} = response;

				const siteName = LiferayService.getLiferaySiteName().replace(
					'/web/',
					''
				);

				const methodList = items.map((item) => ({
					checked: false,
					image: `/webdav/${siteName}/document_library/${item.key.replace(
						'-',
						'_'
					)}.svg`,
					options: [
						{
							checked: true,
							description: `Save $${Number(
								product.promo
							).toLocaleString('en-US')}`,
							id: 0,
							title: `Pay in full – $${Number(
								product.price
							).toLocaleString('en-US')}`,
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

	const onPayNow = async (method) => {
		await updateOrderPaymentMethod(method.value, product.price, orderId);

		window.location.href = `${origin}${LiferayService.getLiferaySiteName()}/congrats`;
	};

	const showOptions = (method) =>
		method.options.map((option, index) => (
			<div
				className={classNames('payment-method-option', {
					selected: option.checked,
				})}
				key={index}
				onClick={() => onSelectedOption(option.id)}
			>
				<div className="card-container">
					<div className="card-content">
						<p className="card-title">{option.title}</p>

						<p className="card-description">{option.description}</p>
					</div>
				</div>
			</div>
		));

	const checkedMethod = methods.find(({checked}) => checked);

	return (
		<div className="payment-method-container">
			<div className="payment-method-row">
				<h3>Payment Method</h3>

				{methods.map((method, index) => (
					<div className="payment-method" key={index}>
						<RadioButton
							onSelected={onSelectedMethod}
							selected={method.checked}
							value={method.value}
						>
							<>
								<div className="image">
									<div>
										<img src={method.image} />
									</div>
								</div>

								<p>{method.title}</p>
							</>
						</RadioButton>
					</div>
				))}
			</div>

			<div className="payment-method-row">
				{checkedMethod && (
					<>
						<h3>Billing Options</h3>

						{checkedMethod.options.length ? (
							<>
								<div className="payment-method-options">
									{showOptions(checkedMethod)}
								</div>
								<div className="agree-check">
									<div className="check">
										<input
											name="agree-check"
											onChange={() => setAgree(!agree)}
											type="checkbox"
											value={agree}
										/>
									</div>

									<p>
										{'I have read and agree to the '}

										<strong>
											Raylife Terms and Conditions
										</strong>
									</p>
								</div>
								<div className="payment-button">
									<button
										className="btn btn-secondary"
										disabled={!agree}
										onClick={() => onPayNow(checkedMethod)}
									>
										Pay Now
									</button>
								</div>
								{checkedMethod.value === 'paypal' && (
									<p className="option-message">
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
