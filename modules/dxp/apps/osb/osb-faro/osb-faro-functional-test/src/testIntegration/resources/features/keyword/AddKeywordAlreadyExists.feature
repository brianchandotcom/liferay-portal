@spira_Interest_Keywords_Blocklist @Keywords @Interest_Keywords_Blocklist @team_FARO @priority_3
Feature: Add a keyword that already exists
	As a Business User, I should not be able to add a keyword that already exists

	Background: [Setup] Navigate to Interests Topic
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Keywords" page

	Scenario: Add keyword already exists
		Given I click the "Add Keyword" button
		And I name the keyword "test"
		And I click the "Send" button
		And I close the alert
		When I click the "Add Keyword" button
		And I name the keyword "test"
		And I click the "Send" button
		Then I should see an info alert saying "Info:test Already belong to the blocklist."
		And I should only see keyword named "test" in the table