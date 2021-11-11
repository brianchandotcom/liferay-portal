import classNames from 'classnames';
import Footer from './components/Footer';
import Header from './components/Header';
const Layout = ({
	children,
	footerProps,
	headerProps,
	headerSkeleton,
	mainStyles,
}) => (
	<div className="border d-flex flex-column mt-5 mx-auto onboarding rounded-lg shadow-lg">
		{headerProps ? <Header {...headerProps} /> : headerSkeleton}
		<main
			className={classNames('flex-grow-1', 'overflow-auto', mainStyles)}
		>
			{children}
		</main>

		<Footer {...footerProps} />
	</div>
);
export default Layout;
