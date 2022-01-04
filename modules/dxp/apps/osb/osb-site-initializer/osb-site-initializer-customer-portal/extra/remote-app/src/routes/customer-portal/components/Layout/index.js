import ProjectContacts from '../ProjectContacts';
import QuickLinksPanel from '../QuickLinksPanel';

const Layout = ({children, hasProjectContact, hasQuickLinks, project}) => {
	return (
		<div className="d-flex position-relative w-100">
			<div className="w-100">
				{hasProjectContact && (
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

			{hasQuickLinks && (
				<QuickLinksPanel accountKey={project.accountKey} />
			)}
		</div>
	);
};

export default Layout;
