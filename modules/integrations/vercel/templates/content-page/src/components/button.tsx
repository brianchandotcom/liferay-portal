import Link from "next/link";
import { PropsWithChildren } from "react";

export const Button = (
  props: PropsWithChildren<
    {
      active?: boolean;
    } & ({ onClick: () => void } | { href: string; external?: boolean })
  >
) => {
  const { children, active } = props;
  const className = `button ${active ? "button--active" : ""}`;

  if ("href" in props) {
    return (
      <Link
        href={props.href}
        className={className}
        target={props.external ? "_blank" : undefined}
      >
        {children}
      </Link>
    );
  }

  return (
    <button onClick={props.onClick} className={className}>
      {children}
    </button>
  );
};
