@spira_Interest_Keywords_Blocklist @Keywords @Interest_Keywords_Blocklist @team_FARO @priority_4
Feature: Add multiple keywords
	As a Business User, I should be able to add multiple keywords

	Background: [Setup] Navigate to Interest Topics
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Keywords" page

	Scenario: Add multiple keywords to blocklist
		Given I click the "Add Keyword" button
		When I name multiple keywords:
			| block |
			| test  |
		And I click the "Send" button
		Then I should see the keyword named "block" in the table
		And I should see the keyword named "test" in the table