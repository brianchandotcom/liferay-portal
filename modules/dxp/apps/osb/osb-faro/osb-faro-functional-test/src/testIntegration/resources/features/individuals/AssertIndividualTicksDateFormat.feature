@spira_Individuals @Individuals @team_FARO @priority_3
Feature: Assert Chart ticks for Individuals Metrics standardized
	As a Business User, I should be able to see the chart ticks for Individuals Metrics standardized

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario Outline: Individuals metrics with hour format
		Given I go to the "Individuals" page
		When I click the "Last 30 days" dropdown in the "Active Individuals" card
		And I click the "More Preset Periods" button
		And I click the "<dateRange>" button
		Then I should not be able to click the "W" button
		And I should see standardized granularity of "h a"

		Examples:
			|         dateRange         |
			|        Last 24 hours      |
			|         Yesterday         |

	Scenario Outline: Individuals metrics with day, week and month formats
		Given I go to the "Individuals" page
		When I click the "Last 30 days" dropdown in the "Active Individuals" card
		And I click the "More Preset Periods" button
		And I click the "<dateRange>" button
		And I click the "<granularity>" button
		Then I should see standardized granularity of "<pattern>"

		Examples:
			|      dateRange     |  granularity |  pattern  |
			|    Last 7 days     |       D      |   MMM d   |
    	   #|    Last 7 days     |       W      | MMM d - d |
     	   #|    Last 7 days     |       M      |    MMM    |
			|    Last 28 days    |       D      |   MMM d   |
			|    Last 28 days    |       W      | MMM d - d |
			|    Last 28 days    |       M      |    MMM    |
			|    Last 30 days    |       D      |   MMM d   |
			|    Last 30 days    |       W      | MMM d - d |
			|    Last 30 days    |       M      |    MMM    |
			|    Last 90 days    |       D      |   MMM d   |
			|    Last 90 days    |       W      | MMM d - d |
			|    Last 90 days    |       M      |    MMM    |
			|    Last 180 days   |       D      |   MMM d   |
			|    Last 180 days   |       W      | MMM d - d |
			|    Last 180 days   |       M      |    MMM    |
			|    Last Year       |       D      |   MMM d   |
			|    Last Year       |       W      | MMM d - d |
			|    Last Year       |       M      |    MMM    |