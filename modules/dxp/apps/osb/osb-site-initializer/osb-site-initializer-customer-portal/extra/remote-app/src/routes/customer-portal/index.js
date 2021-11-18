import CustomerPortalStyles from '~/routes/customer-portal/styles/app.scss';
import {AppProvider} from './context';
import Pages from './pages';

const CustomerPortal = (props) => {
	return (
		<>
			<style>{CustomerPortalStyles}</style>
			<AppProvider {...props}>
				<Pages />
			</AppProvider>
		</>
	);
};

export default CustomerPortal;
