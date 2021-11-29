import EnterpriseSearchStyles from '~/routes/enterpriseSearch/styles/app.scss';
import {AppContextProvider} from './context';

import Pages from './pages';

const EnterpriseSearch = (props) => {
	return (
		<>
			<style>{EnterpriseSearchStyles}</style>
			<AppContextProvider {...props}>
				<Pages />
			</AppContextProvider>
		</>
	);
};

export default EnterpriseSearch;
