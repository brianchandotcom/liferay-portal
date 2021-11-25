import '../../routes/customer-portal/styles/app.scss';

import {AppContextProvider} from './context';
import Pages from './pages';

const CustomerPortal = (props) => {
	return (
		<AppContextProvider {...props}>
			<Pages />
		</AppContextProvider>
	);
};

export default CustomerPortal;
