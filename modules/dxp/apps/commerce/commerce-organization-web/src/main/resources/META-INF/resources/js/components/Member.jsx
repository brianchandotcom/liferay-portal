import React from 'react';
import PropTypes from 'prop-types';

function Member(props) {
	const {
		name,
		email: role,
		imageUrl
	} = props.member;

	return(
		<li role="button" tabIndex="-1" className="member">
			<span className="member-picture"
				  style={{background: `url(/image${imageUrl}) center no-repeat #CCC`}}></span>
			<span className="member-data">
				<p className="member-data-name">{name}</p>
				<p>
					<span className="member-data-role">{role}</span>
				</p>
			</span>
		</li>
	);
}

Member.defaultProps = {
	pictureUrl: '',
	name: '',
	role: '',
	tabIndex: 5
};

Member.propTypes = {
	pictureUrl: PropTypes.string,
	name: PropTypes.string,
	role: PropTypes.string,
	tabIndex: PropTypes.number
};

export default Member;
