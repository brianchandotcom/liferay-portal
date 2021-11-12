import CustomerPortalStyles from '~/routes/customer-portal/styles/app.scss';

import Home from './pages/Home';

const CustomerPortal = () => {
	return (
		<>
			<style>{CustomerPortalStyles}</style>
			<Home />
		</>
	);
};

export default CustomerPortal;
