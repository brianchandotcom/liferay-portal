import { Button } from "@clayui/core";
import ClayIcon from '@clayui/icon';

const BaseButton = ({ children, prependIcon, appendIcon, ...props }) => {
    return (
        <Button {...props}>
            {prependIcon && <span className="inline-item inline-item-before">
                <ClayIcon symbol={prependIcon} />
            </span>}
            {children}
            {appendIcon && <span className="inline-item inline-item-after">
                <ClayIcon symbol={appendIcon} />
            </span>}
        </Button>
    );
}

export default BaseButton;