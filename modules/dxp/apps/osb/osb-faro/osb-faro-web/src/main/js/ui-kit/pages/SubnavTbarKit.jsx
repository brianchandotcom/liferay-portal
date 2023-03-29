import Button from 'shared/components/Button';
import React from 'react';
import SubnavTbar from 'shared/components/SubnavTbar';

class SubnavTbarKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<SubnavTbar>
					<SubnavTbar.Item>
						<Button borderless display='unstyled'>
							{'8 Items Selected'}
						</Button>
					</SubnavTbar.Item>
					<SubnavTbar.Item expand>
						<Button borderless display='unstyled'>
							{'SubnavTbar Action'}
						</Button>
					</SubnavTbar.Item>
					<SubnavTbar.Item>
						<Button borderless display='unstyled'>
							{'Undo All'}
						</Button>
					</SubnavTbar.Item>
				</SubnavTbar>

				<SubnavTbar display='primary'>
					<SubnavTbar.Item expand>
						<Button borderless display='unstyled'>
							{'SubnavTbar Action'}
						</Button>
					</SubnavTbar.Item>
					<SubnavTbar.Item>
						<Button borderless display='unstyled'>
							{'Undo All'}
						</Button>
					</SubnavTbar.Item>
				</SubnavTbar>
			</div>
		);
	}
}

export default SubnavTbarKit;
