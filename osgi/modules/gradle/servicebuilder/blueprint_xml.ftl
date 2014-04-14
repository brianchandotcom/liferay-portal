<bean id="${packagePath}.service.${entity.name}${sessionType}Service" class="${packagePath}.service.impl.${entity.name}${sessionType}ServiceImpl" />
<osgi:service ref="${packagePath}.service.${entity.name}${sessionType}Service" interface="${packagePath}.service.${entity.name}${sessionType}Service" />
