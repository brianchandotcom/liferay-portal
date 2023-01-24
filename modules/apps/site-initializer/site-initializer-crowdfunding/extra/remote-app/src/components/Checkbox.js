import React from 'react';

const Checkbox = ({label, checked, onChange}) => {
    return (
        <label className="crowse-checkbox">
            {label}
            <input checked={checked} onChange={onChange} type="checkbox" />
                <span className="checkmark"></span>
        </label>
    )
}

export default Checkbox