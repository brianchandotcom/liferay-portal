import compositions.context_variables as cv
import crypto

test_base_name_truncation = lambda {
    _get_base_name = lambda project_id: str, env_id: str, project_num: str -> str {
        _project_id_full = "{}-{}".format(project_id, env_id)
        _uid_hash = crypto.sha256("{}-{}".format(project_num, _project_id_full))[:6:]
        "{}-{}".format(_project_id_full[:18:], _uid_hash)
    }

    _res_trunc = _get_base_name("long-project-name-123", "prod", "123456789")
    assert len(_res_trunc) == 25, "Base name length must be 25 (18 chars prefix + dash + 6 chars hash)"
}

test_hash_determinism = lambda {
    _get_base_name = lambda project_id: str, env_id: str, project_num: str -> str {
        _project_id_full = "{}-{}".format(project_id, env_id)
        _uid_hash = crypto.sha256("{}-{}".format(project_num, _project_id_full))[:6:]
        "{}-{}".format(_project_id_full[:18:], _uid_hash)
    }

    _res1 = _get_base_name("liferay", "dev", "111")
    _res2 = _get_base_name("liferay", "dev", "111")
    _res3 = _get_base_name("liferay", "prod", "111")

    assert _res1 == _res2, "Hash must be deterministic"
    assert _res1 != _res3, "Hash must be different for different environments"
}
