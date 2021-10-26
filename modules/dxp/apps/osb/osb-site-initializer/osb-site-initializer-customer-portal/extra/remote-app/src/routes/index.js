import CustomerPortal from './customer-portal';
import Onboarding from './onboarding';

const getApps = () => {
	return [Onboarding, CustomerPortal];
};

export {Onboarding, CustomerPortal, getApps};
