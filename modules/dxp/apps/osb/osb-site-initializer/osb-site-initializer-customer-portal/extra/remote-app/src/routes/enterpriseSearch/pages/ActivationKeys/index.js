import { ClaySelect } from '@clayui/form';
import BaseButton from '~/common/components/BaseButton';

const ActivationKeys = () => {

	const options = [
		{
			label: 'Option 1',
			value: '1',
		},
		{
			label: 'Option 2',
			value: '2',
		},
		{
			label: 'Option 3',
			value: '3',
		},
	];

	return (
		<div>

			<div className="mb-3">
				<h1 className="header mb-5">Activation Keys</h1>

				<p className="paragraph">Select an active Liferay Enterprise Search subscription to download the activation key.</p>
			</div>

			<div className="d-flex mb-3">
				<div className="mr-3">
					<label className="ml-3" htmlFor="subscription">Subscription</label>

					<ClaySelect
						aria-label="Subscription"
						className="subscription"
						id="subscription"
					>

						{options.map((item) => (
							<ClaySelect.Option
								key={item.value}
								label={item.label}
								value={item.value}
							/>
						))}

					</ClaySelect>
				</div>

				<div>
					<label className="ml-3" htmlFor="subscription-term">Subscription Term</label>

					<ClaySelect
						aria-label="Subscription Term"
						className="subscription-term"
						id="subscription-term">

						{options.map((item) => (
							<ClaySelect.Option
								key={item.value}
								label={item.label}
								value={item.value}
							/>
						))}

					</ClaySelect>
				</div>
			</div>

			<BaseButton
				displayType="secondary"
				prependIcon="download" >Download Key</BaseButton>
		</div>

	);
};


export default ActivationKeys;
