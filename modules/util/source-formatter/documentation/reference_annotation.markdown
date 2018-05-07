## @[Reference](https://osgi.org/javadoc/r5/enterprise/org/osgi/service/component/annotations/Reference.html)

[policy](https://osgi.org/javadoc/r5/enterprise/org/osgi/service/component/annotations/ReferencePolicy.html)
controls in what way the new target service is binded to the component.
- `STATIC`: The STATIC policy is the most simple policy and is the default
policy. A component instance never sees any of the dynamics. Component
configurations are deactivated before any bound service for a reference having a
STATIC policy becomes unavailable. If a target service is available to replace
the bound service which became unavailable, the component configuration must be
reactivated and bound to the replacement service.
- `DYNAMIC`: The DYNAMIC policy is slightly more complex since the component
implementation must properly handle changes in the set of bound services. With
the DYNAMIC policy, SCR can change the set of bound services without
deactivating a component configuration. If the component uses the event strategy
to access services, then the component instance will be notified of changes in
the set of bound services by calls to the bind and unbind methods.

[policyOption](https://osgi.org/javadoc/r5/enterprise/org/osgi/service/component/annotations/ReferencePolicyOption.html)
controls how a new target service is going to be binded to the component.
- `RELUCTANT` : When a new target service for a reference becomes available,
references having the RELUCTANT policy option for the STATIC policy or the
DYNAMIC policy with a unary cardinality will ignore the new target service.
References having the DYNAMIC policy with a multiple cardinality will bind the
new target service.
- `GREEDY`: When a new target service for a reference becomes available,
references having the GREEDY policy option will bind the new target service.

#### When using a `GREEDY` policy option, we should never use a `STATIC` policy. The component will bind to new target services, but every time the component needs to be deactivated and re-activated. This is not only heavy, but also could lead to many race conditions.