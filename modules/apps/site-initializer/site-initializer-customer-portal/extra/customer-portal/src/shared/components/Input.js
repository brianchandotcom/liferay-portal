import ClayForm, { ClayInput } from "@clayui/form";
import { useField } from "formik";
import { email, required, validate } from "../utils/validations.form";

const Input = ({ label, groupStyle, helper, validations, ...props }) => {
    if (props.type === "email") {
        validations = validations ? [(value) => email(value), ...validations] : [(value) => email(value)];
    }

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
                <ClayInput {...field} {...props} />
            </label>
            {helper && <div className="text-neutral-3 text-paragraph-sm ml-3 pl-3">
                {helper}
            </div>}
        </ClayForm.Group>
    );
};

export default Input;