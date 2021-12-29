import {useCustomerPortal} from '../../context';
import ProjectContacts from '../ProjectContacts';
import QuickLinksPanel from '../QuickLinksPanel';

const Layout = ({children, hasProjectContacts, hasQuickLinks}) => {
	const [{project}] = useCustomerPortal();

	return (
		<div className="d-flex position-relative w-100">
			<div className="w-100">
				{hasProjectContacts && (
					<ProjectContacts
						contact={{
							email: project.liferayContactEmailAddress,
							name: project.liferayContactName,
							role: project.liferayContactRole,
						}}
					/>
				)}

				{children}
			</div>

			{hasQuickLinks && <QuickLinksPanel />}
		</div>
	);
};

export default Layout;
