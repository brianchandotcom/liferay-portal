import ActivationKeysLayout from '../../components/ActivationKeysLayout';

const EnterpriseSearch = ({accountKey}) => {
	return (
		<ActivationKeysLayout>
			<ActivationKeysLayout.Inputs
				accountKey={accountKey}
				productKey="enterprise-search"
				productTitle="Enterprise Search"
			/>
		</ActivationKeysLayout>
	);
};

export default EnterpriseSearch;
