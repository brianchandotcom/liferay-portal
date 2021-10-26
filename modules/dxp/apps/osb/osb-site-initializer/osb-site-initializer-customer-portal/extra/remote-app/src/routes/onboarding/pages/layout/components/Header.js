import classNames from 'classnames';

const Header = ({greetings, helper, title}) => {
	return (
		<header className="p-4">
			{greetings && (
				<h6 className="mb-1 text-brand-primary text-small-caps">
					{greetings}
				</h6>
			)}
			<h2
				className={classNames('text-neutral-0', {
					'mb-0': !helper,
					'mb-1': helper,
				})}
			>
				{title}
			</h2>
			{helper && (
				<p className="mb-0 text-neutral-3 text-paragraph-sm">
					{helper}
				</p>
			)}
		</header>
	);
};

export default Header;
