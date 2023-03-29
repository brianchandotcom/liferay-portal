import Button from 'shared/components/Button';
import getCN from 'classnames';
import Icon, {Size} from 'shared/components/Icon';
import React from 'react';

interface ISelectDataSourceProps extends React.HTMLAttributes<HTMLDivElement> {
	sections: {
		dataSources: {
			iconName: string;
			iconSize: string;
			name: string;
			onClick?: () => void;
			subtitle: string;
			url?: string;
		}[];
		title: string;
	}[];
}

const SelectDataSource: React.FC<ISelectDataSourceProps> = ({
	className,
	sections = []
}) => (
	<div className={getCN('select-data-source-root', className)}>
		{sections.map(({dataSources, title}) => (
			<section key={title}>
				<h4 className='text-uppercase section-title'>{title}</h4>

				<div className='section-items'>
					{dataSources.map(
						({
							iconName,
							iconSize,
							name,
							onClick,
							subtitle,
							url
						}) => (
							<Button
								className='data-source-item'
								display='unstyled'
								href={url}
								key={name}
								onClick={onClick}
							>
								<div className='image'>
									<Icon
										size={iconSize as Size}
										symbol={iconName}
									/>
								</div>

								<div className='details'>
									<div className='title'>
										<h4>{name}</h4>
									</div>

									<div className='subtitle'>{subtitle}</div>
								</div>
							</Button>
						)
					)}
				</div>
			</section>
		))}
	</div>
);

export default SelectDataSource;
