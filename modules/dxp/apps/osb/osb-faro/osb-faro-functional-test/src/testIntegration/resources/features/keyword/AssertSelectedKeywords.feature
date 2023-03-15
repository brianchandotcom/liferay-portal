@spira_Interest_Keywords_Blocklist @Keywords @Interest_Keywords_Blocklist @team_FARO @priority_3
Feature: View only selected keywords
	As a Business User, I should be able to view selected keywords

	Background: [Setup] Navigate to Interests Topic
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Keywords" page

	Scenario: View only selected keywords
		Given I click the "Add Keyword" button
		And I name multiple keywords:
			| test 	|
			| block |
			| key	|
		And I click the "Send" button
		When I click keyword "test" checkbox
		And I click keyword "key" checkbox
		And I click the "View Selected (2)" button
		Then I should not see the keyword named "block" in the table
		And I should see the keyword named "test" in the table
		And I should see the keyword named "key" in the table
		And I click the "Return to List" button
		And I should see the keyword named "block" in the table
		And I should see the keyword named "test" in the table
		And I should see the keyword named "key" in the table