import ClayForm, { ClaySelectWithOption } from "@clayui/form";
import { useField } from "formik";
import { required, validate } from "../utils/validations.form";

const Select = ({ label, groupStyle, helper, validations, ...props }) => {
    if (props.required) {
        validations = validations ? [...validations, (value) => required(value)] : [(value) => required(value)];
    }

    const [field, meta] = useField({
        ...props,
        validate: (value) => validate(validations, value)
    });

    const getStyleStatus = () => {
        if (meta.touched) {
            return meta.error ? " has-error" : " has-success";
        }

        return "";
    };

    return (
        <ClayForm.Group className={`w-100${getStyleStatus()} ${groupStyle ? groupStyle : ""}`}>
            <label>
                {label} {props.required && <span className="ml-n1 text-danger text-paragraph-sm">*</span>}
                <ClaySelectWithOption {...field} {...props} />
            </label>
            {helper && <div>
                {helper}
            </div>}
        </ClayForm.Group>
    );
};

export default Select;