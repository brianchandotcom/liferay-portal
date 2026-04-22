import .backup
import .elasticsearch
import .init
import .k8s_resources
import .managed_service_details
import .overlay
import .security
import .sql
import .storage

_items = backup.items.items + elasticsearch.items.items + init.items.items + k8s_resources.items.items + managed_service_details.items.items + overlay.items.items + security.items.items + sql.items.items + storage.items.items

items = {"items": _items}
