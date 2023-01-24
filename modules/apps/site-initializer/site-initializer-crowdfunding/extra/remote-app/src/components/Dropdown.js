import React, { useState } from 'react';

const Dropdown = ({ index, title, subtitle, children }) => {
    const [active, setActive] = useState(() => index == 1 ? true : false);

    const setActiveDropdown = () =>{
        setActive(prev => !prev);
    }

    return (
        <div className={`crowse-dropdown ${active && 'active'}`}>
            <div className="crowse-dropdown-header" onClick={setActiveDropdown}>
                <span>{index}.</span>
                <div>
                    <h1>{title}</h1>
                    <h2>{subtitle}</h2>
                </div>
            </div>
            <div className="crowse-dropdown-body">
                {children}
            </div>
        </div>
    )
}

export default Dropdown